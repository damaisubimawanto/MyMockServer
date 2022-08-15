package com.example.nostratest.data.remote.dto

import com.example.nostratest.domain.model.Article

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
){
    fun toArticle(): Article {
        return Article(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            source = source.toSource(),
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }
}

