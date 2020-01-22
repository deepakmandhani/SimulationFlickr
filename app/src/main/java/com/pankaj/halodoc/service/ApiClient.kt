package com.pankaj.halodoc.service

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object ApiClient {

    val BASE_URL = "https://hn.algolia.com/api/v1/"
    private var retrofit: Retrofit? = null


    val client: Retrofit
        get() {
            return retrofit ?: run {
                val httpClient = getHttpClient()
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()).build().also { retrofit = it }
            }
        }

    fun getHttpClient(): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return httpClient
    }
}