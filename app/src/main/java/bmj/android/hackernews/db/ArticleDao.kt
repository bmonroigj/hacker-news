package bmj.android.hackernews.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bmj.android.hackernews.model.Article

/**
 * Data access object for [Article]
 */
@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles ORDER BY date DESC")
    fun getArticles(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articles: List<Article>)

    @Query("DELETE FROM articles WHERE id = :articleId")
    suspend fun deleteArticle(articleId: Long)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}