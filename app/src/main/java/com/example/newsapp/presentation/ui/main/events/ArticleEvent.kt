package com.example.newsapp.presentation.ui.main.events

import com.example.newsapp.domain.model.Article

sealed class ArticleEvent {
    data class GetArticleContentEvent( val id: Int): ArticleEvent()
}