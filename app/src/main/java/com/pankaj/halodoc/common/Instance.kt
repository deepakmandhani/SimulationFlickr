package com.pankaj.halodoc.common

import androidx.room.Room
import com.pankaj.halodoc.service.ApiService
import com.pankaj.halodoc.service.AppDataBase
import com.pankaj.halodoc.service.DbService
import com.pankaj.halodoc.view.MyApp

object Instance {

    private val DB_NAME = "db_halo_doc"
    private var appRepository: AppRepository? = null
    private var appDataBase: AppDataBase? = null

    fun getAppRepository(): AppRepository {
        appRepository?.let {
            return it
        }

        val dbContract = DbService(getAppDataBase())

        return AppRepository(ApiService(), dbContract).also {
            appRepository = it
        }
    }

    fun getAppDataBase(): AppDataBase {
        appDataBase?.let {
            return it
        }

        return Room.databaseBuilder(MyApp.instance, AppDataBase::class.java, DB_NAME).build().also {
            appDataBase = it
        }
    }
}