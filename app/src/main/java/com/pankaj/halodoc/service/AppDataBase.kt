package com.pankaj.halodoc.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pankaj.halodoc.model.Hits

@Database(entities = [Hits::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun newsDaoAccess(): NewsDao
}