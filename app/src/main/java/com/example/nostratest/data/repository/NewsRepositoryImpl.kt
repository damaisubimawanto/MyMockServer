package com.example.nostratest.data.repository

import android.util.Log
import com.example.nostratest.data.remote.NewsApi
import com.example.nostratest.domain.model.Response
import com.example.nostratest.domain.repository.NewsRepository
import com.example.nostratest.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {
    override fun getHeadlineNews(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ): Flow<Resource<Response>> = flow {
        emit(Resource.Loading())

        try {
            val headlineNews = newsApi.getNews(country,category,pageSize,page)
            emit(Resource.Success(headlineNews.toResponse()))
        } catch (e: IOException) {
            emit(Resource.Error("${e.localizedMessage}"))
        }
    }
}