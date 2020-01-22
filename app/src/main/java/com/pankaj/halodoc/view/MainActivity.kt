package com.pankaj.halodoc.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pankaj.halodoc.R
import com.pankaj.halodoc.common.Instance
import com.pankaj.halodoc.common.Utils
import com.pankaj.halodoc.model.Hits
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val charLength = 0
    private val waitTimeForDebounce = 800L
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
    }

    private fun observeLiveData() {
        viewModel.observeLiveData().observe(this, Observer {
            Log.d("Response", it.toString())
            setUpdateAdapter(it.hits)
        })

        viewModel.observeFailure().observe(this, Observer {
            Log.d("Failure", it.toString())
            Toast.makeText(this, getString(R.string.failure), Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpdateAdapter(list: List<Hits>) {
        Utils.hideKeyboard(this@MainActivity)
        adapter?.setData(list) ?: run {
            rv_news.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            adapter = NewsRVAdapter(this, list)
            rv_news.adapter = adapter
        }
    }

    private val watcher = object : TextWatcher {
        private var searchFor = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText.equals(searchFor, true) || searchText.length <= charLength) {
                searchFor = searchText
                return
            }

            searchFor = searchText

            coroutineScope.launch(errorHandler) {
                delay(waitTimeForDebounce)  //debounce timeOut
                if (!searchText.equals(searchFor, true) || searchFor.length <= charLength)
                    return@launch
                launch (Dispatchers.Main) {
                    if (Utils.isInternetConnected(this@MainActivity))
                        viewModel.fetchNews(searchFor, 0)
                    else
                        viewModel.fetchNewsFromDb(searchFor)
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
