package com.pankaj.halodoc.service

import androidx.lifecycle.LiveData
import com.pankaj.halodoc.model.Hits

interface DbContract {

    fun fetchNews(query: String) : LiveData<List<Hits>>

    fun insert(query: String, list: List<Hits>)
}