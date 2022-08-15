package com.example.nostratest.data.remote

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class NewsApiTest : BaseApiTest() {
    private lateinit var newsService: NewsApi

    override fun createService() {
        super.createService()
        newsService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create(NewsApi::class.java)
    }

    @Test
    fun getResponseTest() = runBlocking {
        enqueueResponse(filename = "response.json")

        val response = newsService.getNews("id", "technology", 5, 1)
        assertThat(response.articles.size).isEqualTo(5)
    }

    /*@Test
    fun testSuccessfulResponse() = runBlocking {
        enqueueResponse(filename = "response.json")

    }*/
}