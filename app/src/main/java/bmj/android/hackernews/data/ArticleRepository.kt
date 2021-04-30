package bmj.android.hackernews.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import bmj.android.hackernews.api.HackerNewsApi
import bmj.android.hackernews.db.ArticleDatabase
import bmj.android.hackernews.model.Article
import bmj.android.hackernews.model.DeletedArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * The only source of truth. Fetch data from API and stores in DB for retrieval
 */
class ArticleRepository @Inject constructor(
    private val api: HackerNewsApi,
    private val db: ArticleDatabase
) {

    fun fetchArticles(): Flow<PagingData<Article>> {
        val pagingSourceFactory = { db.articleDao().getArticles() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 0
            ),
            remoteMediator = ArticleRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun deleteArticle(articleId: Long, articleUrl: String) {
        db.withTransaction {
            db.articleDao().deleteArticle(articleId)
            db.deletedArticleDao().addDeletedArticle(DeletedArticle(articleId, articleUrl))
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}