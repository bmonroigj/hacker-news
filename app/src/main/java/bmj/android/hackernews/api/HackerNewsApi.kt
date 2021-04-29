package bmj.android.hackernews.api

import bmj.android.hackernews.data.HackerNewsApiResponse
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Fetch recently posted articles from: https://hn.algolia.com/api/v1/search_by_date?query=mobile
 */
interface HackerNewsApi {
    @GET("search_by_date?query=mobile")
    suspend fun fetchArticles(): HackerNewsApiResponse

    companion object {
        private const val BASE_URL = "https://hn.algolia.com/api/v1/"

        fun create(moshi: Moshi): HackerNewsApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(HackerNewsApi::class.java)
        }
    }
}