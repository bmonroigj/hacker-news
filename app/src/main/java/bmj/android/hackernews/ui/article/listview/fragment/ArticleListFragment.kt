package bmj.android.hackernews.ui.article.listview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bmj.android.hackernews.R
import bmj.android.hackernews.databinding.FragmentArticleListBinding
import bmj.android.hackernews.ui.article.listview.adapter.ArticleListAdapter
import bmj.android.hackernews.ui.article.listview.adapter.SwipeToDeleteCallback
import bmj.android.hackernews.ui.article.listview.viewmodel.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private lateinit var binding: FragmentArticleListBinding
    private var fetchJob: Job? = null
    private val adapter = ArticleListAdapter()
    private val articleListViewModel: ArticleListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        binding.articleList.adapter = adapter

        // Handle Swipe-to-delete
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = adapter.getArticle(viewHolder.absoluteAdapterPosition)
                if (article != null) {
                    // Delete article from database triggers adapter data invalidate
                    articleListViewModel.deleteArticle(article.id)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.articleList)

        adapter.addLoadStateListener { loadState ->
            // Show empty list if (state is not loading or initial load or refresh fails) and adapter is empty.
            val isListEmpty =
                (loadState.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.Error) && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            binding.articleList.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.loadingIndicator.isVisible = loadState.mediator?.refresh is LoadState.Loading
        }

        setupRefreshTriggerLayout()
        subscribeUi()

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.articleList.scrollToPosition(0) }
        }
        return binding.root
    }

    private fun setupRefreshTriggerLayout() {
        val metrics = resources.displayMetrics
        binding.refreshTrigger.apply {
            setOnRefreshListener {
                binding.refreshTrigger.isRefreshing = false
                adapter.refresh()
            }
            setColorSchemeResources(R.color.primary_light)
            setDistanceToTriggerSync(256 * metrics.density.toInt())
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.articleList.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.articleList.visibility = View.VISIBLE
        }
    }

    private fun subscribeUi() {
        fetchJob?.cancel()
        fetchJob = viewLifecycleOwner.lifecycleScope.launch {
            articleListViewModel.fetchArticles().collectLatest { articles ->
                binding.refreshTrigger.isRefreshing = false
                adapter.submitData(articles)
                showEmptyList(adapter.itemCount == 0)
            }
        }
    }
}