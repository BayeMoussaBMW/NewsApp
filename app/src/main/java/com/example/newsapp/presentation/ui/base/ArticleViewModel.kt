package com.example.newsapp.presentation.ui.base

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.ui.main.events.ArticleEvent
import com.example.newsapp.respository.ArticlesRepository
import com.example.newsapp.utils.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

const val STATE_KEY_ARTICLE = "article.state.article.key"

@ExperimentalCoroutinesApi
class ArticleViewModel
@ViewModelInject
constructor(
        private val repository: ArticlesRepository,
        @Assisted private val state: SavedStateHandle,
    ): ViewModel(){

    val articles: MutableState<List<Article>> = mutableStateOf(ArrayList())

    val article: MutableState<Article?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

        init {
        for (article in articles.value){
            state.get<MutableState<Article?>>(STATE_KEY_ARTICLE)?.let{ article ->
                this.article.value = article.value
            }
        }

            state.get<Int>(STATE_KEY_ARTICLE)?.let{ article ->
                onTriggerEvent(ArticleEvent.GetArticleContentEvent(article))
            }
        }

    fun onTriggerEvent(event: ArticleEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is ArticleEvent.GetArticleContentEvent -> {
                            if(article != null){
                                getArticle(event.id)
                            }

                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
                loading.value = false
            }
        }
    }

    private suspend fun getArticle(id: Int) {
        loading.value = true
        //delay(1000)
        val result =repository.get()
        for (result in result.articles){
            this.article.value = result
            state.set(STATE_KEY_ARTICLE, result)
            state.set(STATE_KEY_ARTICLE, id)
        }
        loading.value = false
    }

}