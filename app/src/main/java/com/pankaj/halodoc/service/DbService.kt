package com.pankaj.halodoc.service

import androidx.lifecycle.LiveData
import com.pankaj.halodoc.model.Hits

class DbService(private val appDataBase: AppDataBase)  : DbContract {

    override fun fetchNews(query: String): LiveData<List<Hits>> {
        return appDataBase.newsDaoAccess().getNews(query)
    }

    override fun insert(query: String, list: List<Hits>) {
        list.forEach {
            it.searchFor = query
            appDataBase.newsDaoAccess().insert(it)
        }
    }
}