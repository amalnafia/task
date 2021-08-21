package com.example.task.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("id") val type: String,
    @SerializedName("id") val url: String,
    @SerializedName("id") val name: String,
    val downloadStatus: String,
)
