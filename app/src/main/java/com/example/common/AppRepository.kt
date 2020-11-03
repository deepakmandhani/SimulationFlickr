package com.example.common

import androidx.lifecycle.LiveData
import com.example.model.*
import com.example.service.ApiContract

class AppRepository(private val apiContract: ApiContract) {

    fun searchFlickrPhotos(
        query: String,
        page: Int
    ): LiveData<Response<FlickrResponse, FailureResponse>> {
        return apiContract.searchFlickrPhotos(query, page)
    }
}