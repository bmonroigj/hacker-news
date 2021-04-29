package bmj.android.hackernews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contains 'id' of a deleted article
 */
@Entity(tableName = "deleted_articles")
data class DeletedArticle(
    @PrimaryKey val id: Long
)
