package com.example.task.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.enum.ViewStatus
import com.example.task.model.Movies
import com.example.task.model.Resource
import com.example.task.repo.MainRepo
import com.example.task.util.Fail
import com.example.task.util.Success
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class DownloadViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {


    private var moviesListMediatorLiveData: MutableLiveData<Resource<List<Movies>>> =
        MutableLiveData<Resource<List<Movies>>>()

    fun getMoviesList(): MutableLiveData<Resource<List<Movies>>> {
        when (val getMoviesFromRepoResult = mainRepo.getMovies()) {
            is Fail -> {
                //TODO handle api fail
                Log.e("TAG", "VIEW MODEL FAIL")
                moviesListMediatorLiveData.value = Resource(ViewStatus.ERROR, null, "Api Failed")
            }
            is Success -> {
                Log.e("TAG", "VIEW MODEL SUCCESS")
                getMoviesFromRepoResult.response.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                        //TODO Loading until subscribe
                        moviesListMediatorLiveData.value = Resource(ViewStatus.LOADING, null, null)
                    }.subscribe({ list ->
                        moviesListMediatorLiveData.value = Resource(ViewStatus.SUCCESS, list, null)
                    }, {
                        moviesListMediatorLiveData.value =
                            Resource(ViewStatus.ERROR, null, it.message)
                    })
            }
        }
        return moviesListMediatorLiveData
    }

    fun getAdapterList(): MutableList<Movies> {
        val list: MutableList<Movies> = ArrayList<Movies>()
        list.add(Movies(1,
            "VIDEO",
            "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4",
            "Video 1", "Downloading"))
        list.add(Movies(2,
            "VIDEO",
            "https://bestvpn.org/html5demos/assets/dizzy.mp4",
            "Video 2", "Downloaded"))
        list.add(Movies(3,
            "PDF",
            "https://kotlinlang.org/docs/kotlin-reference.pdf",
            "PDF 3", "Downloaded"))
        list.add(Movies(4,
            "VIDEO",
            "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
            "Video 4", "Downloaded"))
        list.add(Movies(5,
            "VIDEO",
            "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
            "PDF 5", "Downloaded"))
        list.add(Movies(6,
            "VIDEO",
            "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
            "Video 6", "Downloaded"))
        list.add(Movies(7,
            "VIDEO",
            "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
            "Video 7", "Downloaded"))
        list.add(Movies(8,
            "VIDEO",
            "https://storage.googleapis.com/exoplayer-test-media-1/mp4/frame-counter-one-hour.mp4",
            "Video 8", "Downloaded"))
        return list
    }

}