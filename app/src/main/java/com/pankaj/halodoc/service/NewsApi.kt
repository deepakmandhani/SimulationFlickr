package com.pankaj.halodoc.service

import com.pankaj.halodoc.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("search")
    fun fetchNews(@Query("query") query: String,
                  @Query("page") page: Int) : Call<News>
}