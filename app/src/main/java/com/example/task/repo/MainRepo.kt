package com.example.task.repo

import android.util.Log
import com.example.task.model.Movies
import com.example.task.network.ApiInterface
import com.example.task.util.Fail
import com.example.task.util.Result
import com.example.task.util.Success
import io.reactivex.Observable
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiInterface: ApiInterface) {
    fun getMovies(): Result<Observable<List<Movies>>> {
        return try {
            val response = apiInterface.getMovies()
            Log.e("TAG", "VIEW MODEL SUCCESS")
            Success(response)
        } catch (e: Exception) {
            Log.e("TAG", "VIEW MODEL $e")
            Fail(e)
        }
    }
}