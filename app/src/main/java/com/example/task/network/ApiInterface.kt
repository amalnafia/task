package com.example.task.network

import com.example.task.model.Movies
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiInterface {
    @GET("movies")
    fun getMovies(): Observable<List<Movies>>
}