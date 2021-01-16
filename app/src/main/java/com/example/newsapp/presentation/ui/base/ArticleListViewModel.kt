package com.example.newsapp.presentation.ui.base

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source
import com.example.newsapp.network.model.ArticleDto
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
): ViewModel(){

    val articles: MutableState<List<Article>> = mutableStateOf(ArrayList())

    val liArticle = arrayListOf(
        ArticleDto("TF1", "test test test ",
            "this is the news today, this is the news today,this is the news today ",
            "13/01/2021", Source(
                "val id: String",
                "val name: String"
            ), "info Mat", "http://tf1.com",
            "https://tf1.com"),

        ArticleDto("BFM", "test test test ",
            "this is the news today, this is the news today,this is the news today, this is the news today ",
            "13/01/2021", Source(
                "val id: String",
                "val name: String"
            ), "info Tak", "http://dakaractu.com",
            "https://bfmtv.com"),
        ArticleDto("BFM Business", "test test test ",
            "this is the news today, this is the news today,this is the news today, this is the news today ",
            "13/01/2021", Source(
                "val id: String",
                "val name: String"
            ), "info Tak", "http://dakaractu.com",
            "https://bfmtv.com"),

        ArticleDto("BBC", "test test test ",
            "this is the news today, this is the news today,this is the news today ",
            "13/01/2021", Source(
                "val id: String",
                "val name: String"
            ), "info Tak", "http://bbcnews.com",
            "https://bbcnews.com"),

        ArticleDto("CNEWS", "test test test ",
            "this is the news today, this is the news today,this is the news today, this is the news today ",
            "13/01/2021", Source(
                "val id: String",
                "val name: String"
            ), "info Tak", "http://dakaractu.com",
            "https://cnews.com"),

        ArticleDto("DAKAR ACTUS", "test test test ",
            "this is the news today, this is the news today,this is the news today, this is the news today ",
            "13/01/2021", Source(
                "val id: String",
                "val name: String"
            ), "info Tak", "http://dakaractu.com",
            "https://dakaractu.com")
    )

    val loading = mutableStateOf(false)

    init {
        onTriggerEvent(GetArticleListEvent())
    }

        fun onTriggerEvent(event: ArticleListEvent){
        viewModelScope.launch{
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

    private suspend fun getResult(){
        val results = repository.get()
        val articles = results.articles
        appendArticles(articles)
    }

    private fun appendArticles(articledtolist: List<Article>){
       this.articles.value = articledtolist
    }
}

