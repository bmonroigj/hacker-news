package bmj.android.hackernews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_articles")
data class DeletedArticle(
    @PrimaryKey val id: Long
)
