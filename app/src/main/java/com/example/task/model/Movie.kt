package com.example.task.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    val downloadStatus: String,
)
