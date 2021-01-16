package com.example.newsapp.utils

interface DomainMapperArticle<T, DomainModel> {

    fun mapArticleDomainModel(model: T): DomainModel
    fun mapFromArticleDomainModel(domainModel: DomainModel): T
}