package com.pankaj.halodoc.view

import android.util.Log
import androidx.lifecycle.*
import com.pankaj.halodoc.common.AppRepository
import com.pankaj.halodoc.model.FailureResponse
import com.pankaj.halodoc.model.News
import kotlinx.coroutines.*

class AppViewModel(private val repo: AppRepository) : ViewModel() {

    private val mediatorLiveData = MediatorLiveData<News>()
    private val failureResponse = MutableLiveData<FailureResponse>()
    private var job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("Error", exception.toString())
    }

    fun observeLiveData(): LiveData<News> {
        return mediatorLiveData
    }

    fun observeFailure(): LiveData<FailureResponse> {
        return failureResponse
    }

    fun fetchNews(query: String, page: Int) {
        val liveData = repo.fetchNews(query, page)
        mediatorLiveData.addSource(liveData) {
            it.obj?.let {
                if (!it.hits.isNullOrEmpty())
                    coroutineScope.launch(errorHandler) {
                        repo.insertToDb(query, it.hits)
                    }
                mediatorLiveData.postValue(it)
            } ?: run {
                failureResponse.postValue(it.failure)
            }
            mediatorLiveData.removeSource(liveData)
        }
    }

    fun fetchNewsFromDb(query: String) {
        val liveData = repo.fetchNewsFromDb(query)
        mediatorLiveData.addSource(liveData) {
            it?.let {
                mediatorLiveData.postValue(News(hits = it))
            } ?: run {
                failureResponse.postValue(FailureResponse())
            }
            mediatorLiveData.removeSource(liveData)
        }
    }

    class AppViewModelFactory(private val repo: AppRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AppViewModel(repo) as T
        }
    }
}