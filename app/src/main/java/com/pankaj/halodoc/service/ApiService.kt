package com.pankaj.halodoc.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pankaj.halodoc.model.FailureResponse
import com.pankaj.halodoc.model.News
import com.pankaj.halodoc.model.Response
import retrofit2.Call
import retrofit2.Callback

class ApiService : ApiContract {

    private val apiService by lazy {
        ApiClient.client.create(NewsApi::class.java)
    }

    override fun fetchNews(query: String, page: Int): LiveData<Response<News, FailureResponse>> {
        val liveData = MutableLiveData<Response<News, FailureResponse>>()
        apiService.fetchNews(query, page).enqueue(object : Callback<News> {

            override fun onFailure(call: Call<News>, t: Throwable) {
                liveData.value = Response(null, FailureResponse(t))
            }

            override fun onResponse(call: Call<News>, response: retrofit2.Response<News>) {
                if (response.isSuccessful) {
                    liveData.value = Response(response.body(), null)
                } else {
                    liveData.value = Response(null, FailureResponse(response.errorBody()))
                }
            }
        })
        return liveData
    }

}