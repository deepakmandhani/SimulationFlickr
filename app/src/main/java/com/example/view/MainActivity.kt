package com.example.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.common.Instance
import com.example.common.Utils
import com.example.model.Photo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var searchFor = ""
    private var nextPage: Int = 0
    private var isLoading = false
    private var isLastPage = false
    private val charLength = 0
    private val waitTimeForDebounce = 500L
    private var adapter: NewsRVAdapter? = null
    private var job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("Error", exception.toString())
    }
    private val viewModel: AppViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModel.AppViewModelFactory(Instance.getAppRepository())
        )
            .get(AppViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeLiveData()
        et_search.addTextChangedListener(watcher)
        setUpAdapter(emptyList())
        showDefaultView()
    }

    private fun observeLiveData() {
        viewModel.observeLiveData().observe(this, Observer {
            Log.d("Response", it.toString())
            if (it.photo.isNullOrEmpty() && nextPage == 0) {
                showDefaultView()
                return@Observer
            }
            updateAdapter(it.photo)
            isLoading = false
            if (nextPage == it.pages)
                isLastPage = true
            nextPage = it.page + 1
        })

        viewModel.observeFailure().observe(this, Observer {
            Log.d("Failure", it.toString())
            isLoading = false
            Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpAdapter(list: List<Photo>) {
        Utils.hideKeyboard(this@MainActivity)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_photos.layoutManager = layoutManager
        adapter = NewsRVAdapter(this, list)
        rv_photos.adapter = adapter

        rv_photos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 10
                    ) {
                        isLoading = true
                        viewModel.searchFlickrPhotos(searchFor, nextPage)
                    }
                }
            }
        })
    }

    private fun updateAdapter(list: List<Photo>) {
        rv_photos.visibility = View.VISIBLE
        tv_no_result.visibility = View.GONE
        if (nextPage == 0)
            adapter?.setData(list)
        else
            adapter?.addData(list)
    }

    private fun showDefaultView() {
        tv_no_result.text = getString(R.string.eq_try_with_diff_keyword)
        tv_no_result.visibility = View.VISIBLE
        rv_photos.visibility = View.GONE
    }

    private val watcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText.equals(searchFor, true) || searchText.length <= charLength) {
                showDefaultView()
                searchFor = searchText
                return
            }

            searchFor = searchText

            coroutineScope.launch(errorHandler) {
                delay(waitTimeForDebounce)
                if (!searchText.equals(searchFor, true) || searchFor.length <= charLength)
                    return@launch
                launch(Dispatchers.Main) {
                    isLoading = true
                    nextPage = 0
                    if (Utils.isInternetConnected(this@MainActivity))
                        viewModel.searchFlickrPhotos(searchFor, nextPage)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
