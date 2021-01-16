package com.example.newsapp.presentation.ui.main.events

sealed class ArticleEvent {
    data class GetArticleContentEvent( val id: Int): ArticleEvent()
}