package bmj.android.hackernews.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bmj.android.hackernews.model.DeletedArticle

@Dao
interface DeletedArticleDao {

    @Query("SELECT * FROM deleted_articles")
    suspend fun getDeletedArticles(): List<DeletedArticle>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDeletedArticle(deletedArticle: DeletedArticle)
}