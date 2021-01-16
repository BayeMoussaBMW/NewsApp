package com.example.newsapp.domain.model


import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)