package bmj.android.hackernews.di

import bmj.android.hackernews.api.HackerNewsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

/**
 * Network and JSON parser instance provider for Dependency Injection
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }

    @Singleton
    @Provides
    fun provideHackerNewsApi(moshi: Moshi): HackerNewsApi {
        return HackerNewsApi.create(moshi)
    }
}
