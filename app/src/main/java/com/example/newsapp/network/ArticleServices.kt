package com.example.newsapp.network

import com.example.newsapp.network.model.ArticlessDto
import com.example.newsapp.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ArticleServices {
    @Headers("Content-Type: application/json")
    @GET("v2/top-headlines")// + "country=us" + "&apiKey=547c6b4826344d68b4c7310f9762c2b1")
    suspend fun getArticles(
            @Query("country")
            countryCode: String = "fr",
            @Query("apiKey")
            apiKey: String = API_KEY
    ): ArticlessDto
}