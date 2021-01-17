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
import com.example.newsapp.domain.model.Articles
import com.example.newsapp.presentation.ui.main.events.ArticleListEvent
import com.example.newsapp.presentation.ui.main.events.ArticleListEvent.GetArticleListEvent
import com.example.newsapp.respository.ArticlesRepository
import com.example.newsapp.utils.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ArticleListViewModel
@ViewModelInject
constructor(
        private val repository: ArticlesRepository,
        @Assisted private val state: SavedStateHandle,
): ViewModel(){

    val articles: MutableState<List<Article>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    init {
        state.get<Articles>(STATE_KEY_ARTICLE)?.let{ article ->
            this.articles.value = article.articles
        }
        onTriggerEvent(GetArticleListEvent())
    }

        fun onTriggerEvent(event: ArticleListEvent){
        viewModelScope.launch{
            restoreState()
            try {
                when(event){
                    is GetArticleListEvent -> {
                        getResult()
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


    private suspend fun restoreState(){
        viewModelScope.launch {
            loading.value = true
            val results: ArrayList<Article> = ArrayList()
            for(p in articles.value){
                val result = repository.get()
                results.addAll(result.articles)
            }
            articles.value = results
        }
    }

    private suspend fun getResult(){
        val results = repository.get()
        val articles = results.articles
        appendArticles(articles)
        state.set(STATE_KEY_ARTICLE, this.articles.value)
    }

    private fun appendArticles(articledtolist: List<Article>){
       this.articles.value = articledtolist
    }
}

