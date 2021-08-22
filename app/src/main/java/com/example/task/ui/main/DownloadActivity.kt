package com.example.task.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.task.R
import com.example.task.application.MyApplication
import com.example.task.enum.ViewStatus.*
import kotlinx.android.synthetic.main.activity_download.*
import javax.inject.Inject

class DownloadActivity : AppCompatActivity() {
    @Inject
    lateinit var downloadViewModel: DownloadViewModel

    @Inject
    lateinit var downloadAdapter: DownloadAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        (application as MyApplication).appComponent.inject(this)

        initRecycler()
        subscribeToMoviesListObserver()
    }

    private fun subscribeToMoviesListObserver() {

        downloadViewModel.getMoviesList().observe(this, {
            when (it.status) {
                LOADING -> Log.d("TAG", "subscribeToMoviesListObserver: " + it.status)
                ERROR -> Log.d("TAG", "subscribeToMoviesListObserver: " + it.message)
                SUCCESS -> {
                    //when response is success TODO
                }
            }
        })
        simulateFakeSuccessResponse()
    }

    private fun simulateFakeSuccessResponse() {
        Handler().postDelayed({
            //simulate that response is success
            progressBar.visibility = View.GONE
            downloadAdapter.setContext(applicationContext)
            downloadAdapter.setAdapterModel(downloadViewModel.getAdapterList())
        }, 1000)
    }

    private fun initRecycler() {
        recyclerview.adapter = downloadAdapter
    }
}