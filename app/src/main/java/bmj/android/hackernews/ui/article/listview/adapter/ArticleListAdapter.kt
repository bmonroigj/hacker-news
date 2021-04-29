package bmj.android.hackernews.ui.article.listview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bmj.android.hackernews.databinding.ItemArticleListBinding
import bmj.android.hackernews.model.Article
import bmj.android.hackernews.ui.article.listview.fragment.ArticleListFragmentDirections

class ArticleListAdapter :
    PagingDataAdapter<Article, ArticleListAdapter.ArticleViewHolder>(ARTICLE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    fun getArticle(position: Int): Article? = getItem(position)

    class ArticleViewHolder(
        val binding: ItemArticleListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.article?.let { article ->
                    navigateToArticle(article, view)
                }
            }
        }

        private fun navigateToArticle(
            article: Article,
            view: View
        ) {
            val direction =
                ArticleListFragmentDirections.actionArticleListFragmentToArticleViewFragment(article.url)
            view.findNavController().navigate(direction)
        }

        fun bind(item: Article) {
            binding.apply {
                article = item
                executePendingBindings()
            }
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                oldItem == newItem
        }
    }
}