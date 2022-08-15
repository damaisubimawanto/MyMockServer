package com.example.nostratest.di

import com.example.nostratest.data.remote.NewsApi
import com.example.nostratest.data.repository.NewsRepositoryImpl
import com.example.nostratest.domain.repository.NewsRepository
import com.example.nostratest.domain.usecase.GetHeadlinesNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetHeadlineUsecase(repository: NewsRepository) : GetHeadlinesNews {
        return GetHeadlinesNews(repository)
    }


    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository{
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}