package com.example.newsapp.presentation.ui.base

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.ui.main.events.ArticleEvent
import com.example.newsapp.respository.ArticlesRepository
import com.example.newsapp.utils.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ArticleViewModel

@ViewModelInject
constructor(
    private val repository: ArticlesRepository,
    ): ViewModel(){

    val article: MutableState<Article?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

        init {

        }

    fun onTriggerEvent(event: ArticleEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is ArticleEvent.GetArticleContentEvent -> {
                       if(article.value == null){
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



    private suspend fun getArticle(id: Int){
        loading.value = true
        delay(1000)
        val article = repository.getContentArticle()
        this.article.value = article
    }

}