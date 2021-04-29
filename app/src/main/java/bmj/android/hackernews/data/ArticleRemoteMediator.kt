package bmj.android.hackernews.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import bmj.android.hackernews.api.HackerNewsApi
import bmj.android.hackernews.db.ArticleDatabase
import bmj.android.hackernews.model.Article
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val api: HackerNewsApi,
    private val db: ArticleDatabase
) : RemoteMediator<Int, Article>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        try {
            val response = api.fetchArticles()
            val deletedArticles = db.deletedArticleDao().getDeletedArticles()
                .map { deletedArticle -> deletedArticle.id }
            val articles =
                response.articles.filter { article -> !deletedArticles.contains(article.id) }
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.articleDao().deleteAll()
                }
                db.articleDao().insertAll(articles)
            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


}