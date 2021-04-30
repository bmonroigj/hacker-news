package bmj.android.hackernews.ui.article.listview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bmj.android.hackernews.data.ArticleRepository
import bmj.android.hackernews.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {
    private var currentArticlesResult: Flow<PagingData<Article>>? = null

    fun fetchArticles(): Flow<PagingData<Article>> {
        val lastResult = currentArticlesResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Article>> =
            repository.fetchArticles().cachedIn(viewModelScope)
        currentArticlesResult = newResult
        return newResult
    }

    fun deleteArticle(articleId: Long, articleUrl: String) {
        viewModelScope.launch {
            repository.deleteArticle(articleId, articleUrl)
        }
    }
}