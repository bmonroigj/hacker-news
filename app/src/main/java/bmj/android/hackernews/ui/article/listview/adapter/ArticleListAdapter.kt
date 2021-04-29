package bmj.android.hackernews.ui.article.listview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bmj.android.hackernews.databinding.ItemArticleListBinding
import bmj.android.hackernews.model.Article

class ArticleListAdapter :
    ListAdapter<Article, ArticleListAdapter.ArticleViewHolder>(ARTICLE_COMPARATOR) {

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
        holder.bind(article)
    }

    fun getArticle(position: Int): Article = getItem(position)

    class ArticleViewHolder(
        val binding: ItemArticleListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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