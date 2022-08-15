package com.example.nostratest.data.remote.dto

import com.example.nostratest.domain.model.Response


data class ResponseApi(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
){
    fun toResponse(): Response {
        return Response(
            articles = articles.map { it.toArticle() },
            status = status,
            totalResults = totalResults
        )
    }
}