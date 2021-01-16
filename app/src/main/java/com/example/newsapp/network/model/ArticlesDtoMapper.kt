package com.example.newsapp.network.model


import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Articles
import com.example.newsapp.utils.DomainMapper
import com.example.newsapp.utils.DomainMapperArticle
import javax.inject.Inject

class ArticlesDtoMapper
@Inject
constructor(): DomainMapperArticle<Article, ArticleDto>,
     DomainMapper<Articles, ArticlessDto> {

    override fun mapDomainModel(model: Articles): ArticlessDto {
        return ArticlessDto(
                articles=model.articles,
                status=model.status,
                totalResults=model.totalResults,
        )
    }

    override fun mapFromDomainModel(domainModel: ArticlessDto): Articles {
        return Articles(
                articles=domainModel.articles,
                status=domainModel.status,
                totalResults=domainModel.totalResults,
        )
    }

    fun mapDomainModelList(models: List<Articles>): List<ArticlessDto>{
        return models.map { mapDomainModel(it) }
    }

    override fun mapArticleDomainModel(model: Article): ArticleDto {
        return ArticleDto(
                 author = model.author,
                 content = model.content,
                 description = model.description,
                 publishedAt = model.publishedAt,
                 source = model.source,
                 title = model.title,
                 url = model.url,
                 urlToImage = model.urlToImage
        )
    }


    override fun mapFromArticleDomainModel(domainModel: ArticleDto): Article {
        return Article(
                author = domainModel.author,
                content = domainModel.content,
                description = domainModel.description,
                publishedAt = domainModel.publishedAt,
                source = domainModel.source,
                title = domainModel.title,
                url = domainModel.url,
                urlToImage = domainModel.urlToImage)
    }

    fun mapArticleDomainModelList(models: List<Article>): List<ArticleDto>{
        return models.map { mapArticleDomainModel(it) }
    }




}