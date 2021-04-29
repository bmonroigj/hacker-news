package bmj.android.hackernews.data

import androidx.room.withTransaction
import bmj.android.hackernews.api.HackerNewsApi
import bmj.android.hackernews.db.ArticleDatabase
import bmj.android.hackernews.model.Article
import bmj.android.hackernews.model.DeletedArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val api: HackerNewsApi,
    private val db: ArticleDatabase
) {

    fun getArticles(): Flow<List<Article>> {
        return db.articleDao().getArticles()
    }

    suspend fun fetchArticles() {
        val deletedArticles =
            db.deletedArticleDao().getDeletedArticles().map { deletedArticle -> deletedArticle.id }
        val articles =
            api.fetchArticles().articles.filter { article -> !deletedArticles.contains(article.id) }
        db.withTransaction {
            db.articleDao().deleteAll()
            db.articleDao().insertAll(articles)
        }
    }

    suspend fun deleteArticle(articleId: Long) {
        db.withTransaction {
            db.articleDao().deleteArticle(articleId)
            db.deletedArticleDao().addDeletedArticle(DeletedArticle(articleId))
        }
    }
}