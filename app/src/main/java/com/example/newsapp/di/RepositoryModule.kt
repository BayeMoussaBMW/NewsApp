package com.example.newsapp.di

import com.example.newsapp.network.ArticleServices
import com.example.newsapp.network.model.ArticlesDtoMapper
import com.example.newsapp.respository.ArticlesRepository
import com.example.newsapp.respository.ArticlesRepository_impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        articleService: ArticleServices,
        articlesDtoMapper: ArticlesDtoMapper,
    ): ArticlesRepository {
        return ArticlesRepository_impl(
            articleService = articleService,
            dtoMapper = articlesDtoMapper
        )
    }

}












