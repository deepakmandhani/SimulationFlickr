package com.example.service

import androidx.lifecycle.LiveData
import com.example.model.FailureResponse
import com.example.model.FlickrResponse
import com.example.model.Response

interface ApiContract {

    fun searchFlickrPhotos(
        query: String,
        page: Int
    ): LiveData<Response<FlickrResponse, FailureResponse>>
}