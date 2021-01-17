package com.example.newsapp.respository

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Articles
import com.example.newsapp.network.ArticleServices
import com.example.newsapp.network.model.ArticleDto
import com.example.newsapp.network.model.ArticlesDtoMapper
import com.example.newsapp.network.model.ArticlessDto

class ArticlesRepository_impl (
    private val articleService: ArticleServices,
    private val dtoMapper: ArticlesDtoMapper,
): ArticlesRepository {

    override suspend fun get(): Articles {
       return dtoMapper.mapFromDomainModel(articleService.getArticles())
    }

    /*override suspend fun getContentArticle(id: Int): Article {
        return dtoMapper.mapFromArticleDomainModel(articleService.getArticles() as ArticleDto)
    }*/

    override suspend fun getContentArticle(): Article {
        return dtoMapper.mapFromArticleDomainModel(articleService.getArticles() as ArticleDto)
    }
}