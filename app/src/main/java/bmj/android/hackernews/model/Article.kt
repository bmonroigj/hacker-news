package bmj.android.hackernews.model

import java.util.*

data class Article(
    val id: Long,
    val title: String,
    val url: String,
    val author: String,
    val date: Date
)