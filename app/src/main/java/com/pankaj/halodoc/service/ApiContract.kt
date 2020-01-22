package com.pankaj.halodoc.service

import androidx.lifecycle.LiveData
import com.pankaj.halodoc.model.FailureResponse
import com.pankaj.halodoc.model.News
import com.pankaj.halodoc.model.Response

interface ApiContract {

    fun fetchNews(query: String, page: Int): LiveData<Response<News, FailureResponse>>
}