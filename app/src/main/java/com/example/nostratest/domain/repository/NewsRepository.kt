package com.example.nostratest.domain.repository

import com.example.nostratest.domain.model.Response
import com.example.nostratest.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getHeadlineNews(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ): Flow<Resource<Response>>
}