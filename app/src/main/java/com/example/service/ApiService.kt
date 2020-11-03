package com.example.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.model.FailureResponse
import com.example.model.FlickrResponse
import com.example.model.Response
import retrofit2.Call
import retrofit2.Callback

class ApiService : ApiContract {

    private val apiService by lazy {
        ApiClient.client.create(FlickrApi::class.java)
    }

    override fun searchFlickrPhotos(
        query: String,
        page: Int
    ): LiveData<Response<FlickrResponse, FailureResponse>> {
        val liveData = MutableLiveData<Response<FlickrResponse, FailureResponse>>()
        apiService.searchFlickrPhotos(query, page).enqueue(object : Callback<FlickrResponse> {

            override fun onResponse(
                call: Call<FlickrResponse>,
                response: retrofit2.Response<FlickrResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.value = Response(response.body(), null)
                } else {
                    liveData.value = Response(null, FailureResponse(response.errorBody()))
                }
            }

            override fun onFailure(
                call: Call<FlickrResponse>,
                t: Throwable
            ) {
                liveData.value = Response(null, FailureResponse(t))
            }
        })
        return liveData
    }

}