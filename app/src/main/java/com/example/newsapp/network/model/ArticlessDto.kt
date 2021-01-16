package com.example.newsapp.network.model

import com.example.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

class ArticlessDto(
@SerializedName("articles")
val articles: List<Article>,
@SerializedName("status")
val status: String,
@SerializedName("totalResults")
val totalResults: Int
)