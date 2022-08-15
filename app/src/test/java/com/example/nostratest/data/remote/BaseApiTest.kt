package com.example.nostratest.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule

/**
 * Created by damai.subimawanto on 6/22/2022.
 */
open class BaseApiTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mockWebServer: MockWebServer

    @Before
    open fun createService() {
        mockWebServer = MockWebServer()
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient().newBuilder()
            .addInterceptor(logging)
        return builder.build()
    }

    fun enqueueResponse(filename: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$filename")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }
}