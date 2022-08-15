package com.example.nostratest.domain.model


data class Response(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)