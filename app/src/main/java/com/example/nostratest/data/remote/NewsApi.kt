package com.example.nostratest.data.remote

import com.example.nostratest.data.remote.dto.ResponseApi
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?apiKey=c3749351b52445edaa3e0f9fa150958c")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize : Int,
        @Query("page") page: Int
    ): ResponseApi


    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}