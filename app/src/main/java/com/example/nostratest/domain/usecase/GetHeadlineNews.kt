package com.example.nostratest.domain.usecase

import com.example.nostratest.domain.model.Response
import com.example.nostratest.domain.repository.NewsRepository
import com.example.nostratest.util.Resource
import kotlinx.coroutines.flow.Flow

data class GetHeadlinesNews(
    private val repository: NewsRepository
){
    operator fun invoke(page:Int): Flow<Resource<Response>> {
        val country = "id"
        val category = "technology"
        val pageSize = 5
        return repository.getHeadlineNews(country, category, pageSize, page)
    }
}