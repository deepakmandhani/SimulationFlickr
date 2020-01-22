package com.pankaj.halodoc.common

import androidx.lifecycle.LiveData
import com.pankaj.halodoc.model.FailureResponse
import com.pankaj.halodoc.model.Hits
import com.pankaj.halodoc.model.News
import com.pankaj.halodoc.model.Response
import com.pankaj.halodoc.service.ApiContract
import com.pankaj.halodoc.service.DbContract

open class AppRepository (val apiContract: ApiContract, val dbContract: DbContract) {

    fun fetchNews(query: String, page: Int) : LiveData<Response<News, FailureResponse>> {
        return apiContract.fetchNews(query, page)
    }

    fun fetchNewsFromDb(query: String): LiveData<List<Hits>> {
        return dbContract.fetchNews(query)
    }

    fun insertToDb(query: String, list: List<Hits>) {
        dbContract.insert(query, list)
    }
}