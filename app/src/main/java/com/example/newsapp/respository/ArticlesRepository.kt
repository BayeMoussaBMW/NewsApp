package com.example.newsapp.respository

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Articles
import com.example.newsapp.network.model.ArticlessDto

interface ArticlesRepository {
    suspend fun get(): Articles
    suspend fun getContentArticle(): Article
}