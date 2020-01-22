package com.pankaj.halodoc.service

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import com.pankaj.halodoc.model.Hits

@Dao
interface NewsDao {

//    @Query("SELECT * FROM `hits`")
    @Query("SELECT * FROM hits WHERE searchFor GLOB '*' || :query || '*'")
    fun getNews(query: String): LiveData<List<Hits>>

    @Insert
    fun insert(hits: Hits)
}