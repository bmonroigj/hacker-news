package bmj.android.hackernews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.*

@Entity(tableName = "articles")
@JsonClass(generateAdapter = true, generator = "ArticleJsonAdapter")
data class Article(
    @PrimaryKey val id: Long,
    val title: String,
    val url: String,
    val author: String,
    val date: Date
)