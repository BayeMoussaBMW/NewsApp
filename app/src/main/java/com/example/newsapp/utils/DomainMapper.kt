package com.example.newsapp.utils

interface DomainMapper <T, DomainModel>{

    fun mapDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

}