package com.example.task.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.task.R
import com.example.task.application.MyApplication
import com.example.task.enum.ViewStatus.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)

        initRecycler()
        subscribeToMovieListObserver()
    }

    private fun subscribeToMovieListObserver() {

        mainViewModel.getMoviesList().observe(this, {
            when (it.status) {
                LOADING -> Log.d("TAG", "subscribeToMovieListObserver: " + it.status)
                ERROR -> Log.d("TAG", "subscribeToMovieListObserver: " + it.message)
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
            mainAdapter.setContext(applicationContext)
            mainAdapter.setAdapterModel(mainViewModel.getAdapterList())
        }, 1000)
    }

    private fun initRecycler() {
        recyclerview.adapter = mainAdapter
    }
}