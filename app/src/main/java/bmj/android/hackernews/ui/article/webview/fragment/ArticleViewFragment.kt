package bmj.android.hackernews.ui.article.webview.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import bmj.android.hackernews.R
import bmj.android.hackernews.databinding.FragmentArticleViewBinding
import bmj.android.hackernews.ui.article.webview.ArticleWebViewClient
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleViewFragment : Fragment() {
    private val args: ArticleViewFragmentArgs by navArgs()
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentArticleViewBinding.inflate(inflater, container, false)
        webView = binding.webView

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            setupWebView(binding.progressBar)
            setupBackPressed(requireActivity(), binding.toolBar)
            setupRefresh(binding.swipeRefresh, binding.progressBar)
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    private fun setupBackPressed(activity: FragmentActivity, toolBar: MaterialToolbar) {
        toolBar.setNavigationOnClickListener {
            handOnBackPressed()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handOnBackPressed()
            }
        }
        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        webView.apply {
            webChromeClient = WebChromeClient()
            webViewClient = ArticleWebViewClient(progressBar)
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            }
        }.loadUrl(args.articleUrl)
    }

    private fun setupRefresh(swipeRefresh: SwipeRefreshLayout, progressBar: ProgressBar) {
        val metrics = resources.displayMetrics
        swipeRefresh.apply {
            setOnRefreshListener {
                webView.reload()
                progressBar.visibility = View.VISIBLE
                swipeRefresh.isRefreshing = false
            }
            setColorSchemeResources(R.color.primary_light)
            setDistanceToTriggerSync(256 * metrics.density.toInt())
        }
    }

    private fun handOnBackPressed() {
        webView.stopLoading()
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            val navigationController = findNavController()
            if (navigationController.popBackStack()) {
                navigationController.navigateUp()
            } else {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        const val TAG = "StoryDetailFragment"
    }
}