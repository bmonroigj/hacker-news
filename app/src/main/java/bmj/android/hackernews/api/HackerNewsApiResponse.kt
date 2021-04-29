package bmj.android.hackernews.api

import bmj.android.hackernews.model.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Only get the target values from API JSON response
 */
@JsonClass(generateAdapter = true)
data class HackerNewsApiResponse(
    @Json(name = "hits") val articles: List<Article>,
)
