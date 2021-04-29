package bmj.android.hackernews.ui.article.listview.viewmodel

import androidx.lifecycle.ViewModel
import bmj.android.hackernews.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class ArticleListViewModel : ViewModel() {
    fun fetchArticles(): Flow<List<Article>> {
        // TODO: fetch articles from the repository
        val currentDate = Date()
        return flow {
            emit(
                listOf(
                    Article(
                        1619467851,
                        "Tesla admits its Full Self-Driving technology is a Level 2 system",
                        "https://www.autoblog.com/2021/03/09/tesla-full-self-driving-level-2-sae",
                        "anonymousobvi",
                        currentDate
                    ),
                    Article(
                        1619467518,
                        "iOS 14.5 delivers Unlock iPhone with Apple Watch, new privacy controls, and more",
                        "https://www.apple.com/newsroom/2021/04/ios-14-5-offers-unlock-iphone-with-apple-watch-diverse-siri-voices-and-more/",
                        "janandonly",
                        currentDate
                    ),
                    Article(
                        1619467143,
                        "Tesla admits its Full Self-Driving technology is a Level 2 system",
                        "https://www.autoblog.com/2021/03/09/tesla-full-self-driving-level-2-sae",
                        "anonymousobvi",
                        currentDate
                    ),
                    Article(
                        1619466282,
                        "Phoebus Cartel",
                        "https://en.wikipedia.org/wiki/Phoebus_cartel",
                        "syshum",
                        currentDate
                    ),
                    Article(
                        1619466073,
                        "Tesla admits its Full Self-Driving technology is a Level 2 system",
                        "https://www.autoblog.com/2021/03/09/tesla-full-self-driving-level-2-sae",
                        "anonymousobvi",
                        currentDate
                    )
                )
            )
        }
    }

    fun deleteArticle(articleId: Long) {
        // TODO: Delete article from database
    }
}