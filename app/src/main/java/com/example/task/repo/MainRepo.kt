package com.example.task.repo

import android.util.Log
import com.example.task.enum.Fail
import com.example.task.enum.Result
import com.example.task.enum.Success
import com.example.task.model.Movie
import com.example.task.network.ApiInterface
import io.reactivex.Observable
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiInterface: ApiInterface) {
    fun getMovies(): Result<Observable<List<Movie>>> {
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