package com.example.task.ui.download

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.task.R
import com.example.task.application.MyApplication
import com.example.task.enum.DownloadStatus
import com.example.task.enum.ViewStatus.*
import com.example.task.interfaces.OnItemAdapterClick
import com.example.task.util.DownloadWorker
import kotlinx.android.synthetic.main.activity_download.*
import javax.inject.Inject


class DownloadActivity : AppCompatActivity(), OnItemAdapterClick {
    @Inject
    lateinit var downloadViewModel: DownloadViewModel

    @Inject
    lateinit var downloadAdapter: DownloadAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        (application as MyApplication).appComponent.inject(this)

        requestWritePermission()

        initRecycler()

        subscribeToMoviesListObserver()
    }

    private fun requestWritePermission() {
        when {
            !checkIfPermissionGranted() -> {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
            }
        }
    }

    private fun checkIfPermissionGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun initRecycler() {
        recyclerview.adapter = downloadAdapter
        downloadAdapter.setOnItemAdapterClick(this)
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

    private fun downloadClickedFile(url: String?) {
        val data = Data.Builder()
        data.putString("URL", url)
        val apkWorkRequest: WorkRequest =
            OneTimeWorkRequest.Builder(DownloadWorker::class.java).setInputData(data.build())
                .build()
        WorkManager.getInstance(this).enqueue(apkWorkRequest)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(apkWorkRequest.id)
            .observe(this, { listOfWorkInfo: WorkInfo ->
                val progress = listOfWorkInfo.progress.getInt("Progress", 0)
                downloadViewModel.updateAdapterList(progress, DownloadStatus.DOWNLOADING)
                downloadAdapter.updateList(downloadViewModel.getAdapterList())
                when (listOfWorkInfo.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        Log.d("TAG", "downloadClickedFile: ")
                        downloadViewModel.updateAdapterList(100, DownloadStatus.DOWNLOADED)
                        downloadAdapter.updateList(downloadViewModel.getAdapterList())
                    }
                    WorkInfo.State.FAILED -> {
                        downloadViewModel.updateAdapterList(0, DownloadStatus.FAIL)
                        downloadAdapter.updateList(downloadViewModel.getAdapterList())
                    }
                }
            })
    }

    override fun onItemAdapterClick(itemPosition: Int, itemId: Int, url: String?) {
        if (checkIfPermissionGranted()) {
            downloadClickedFile(url)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            )
                requestWritePermission()
        }
    }
}