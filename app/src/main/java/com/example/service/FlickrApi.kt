package com.example.service

import com.example.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("rest")
    fun searchFlickrPhotos(
        @Query("text") text: String,
        @Query("page") page: Int = 1,
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "062a6c0c49e4de1d78497d13a7dbb360",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Call<FlickrResponse>
}