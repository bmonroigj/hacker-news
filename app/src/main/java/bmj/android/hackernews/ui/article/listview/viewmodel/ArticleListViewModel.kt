package bmj.android.hackernews.ui.article.listview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bmj.android.hackernews.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {
    val articles = repository.getArticles()

    fun fetchArticles() {
        viewModelScope.launch {
            repository.fetchArticles()
        }
    }

    fun deleteArticle(articleId: Long) {
        viewModelScope.launch {
            repository.deleteArticle(articleId)
        }
    }
}