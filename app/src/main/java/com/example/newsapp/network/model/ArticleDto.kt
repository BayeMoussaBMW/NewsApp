package com.example.newsapp.network.model

import com.example.newsapp.domain.model.Source

data class ArticleDto(
        val author: String? = null,
        val content: String? = null,
        val description: String? = null,
        val publishedAt: String? = null,
        val source: Source? = null,
        val title: String? = null,
        val url: String? = null,
        val urlToImage: String? = null
)