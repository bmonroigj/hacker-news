package bmj.android.hackernews.ui.article.listview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bmj.android.hackernews.databinding.FragmentArticleListBinding
import bmj.android.hackernews.ui.article.listview.adapter.ArticleListAdapter
import bmj.android.hackernews.ui.article.listview.adapter.SwipeToDeleteCallback
import bmj.android.hackernews.ui.article.listview.viewmodel.ArticleListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = adapter.getArticle(viewHolder.absoluteAdapterPosition)
                articleListViewModel.deleteArticle(article.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.articleList)

        binding.refreshTrigger.setOnRefreshListener {
            fetchArticles()
            binding.refreshTrigger.isRefreshing = false
        }

        fetchArticles()
        return binding.root
    }

    private fun fetchArticles() {
        fetchJob?.cancel()
        fetchJob = viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.loadingIndicator.visibility = View.VISIBLE
            }
            articleListViewModel.fetchArticles().collectLatest { articles ->
                withContext(Dispatchers.Main) {
                    binding.loadingIndicator.visibility = View.GONE
                }
                adapter.submitList(articles)
            }
        }
    }
}