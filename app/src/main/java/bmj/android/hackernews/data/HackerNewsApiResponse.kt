package bmj.android.hackernews.data

import bmj.android.hackernews.model.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HackerNewsApiResponse(
    @Json(name = "hits") val articles: List<Article>,
)
