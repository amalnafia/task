package com.example.task.model

import com.example.task.enum.DownloadStatus
import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    var downloadStatus: DownloadStatus,
    var downloadPercentage: Int,
)
