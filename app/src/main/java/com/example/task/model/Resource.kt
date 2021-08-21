package com.example.task.model

import com.example.task.enum.ViewStatus

data class Resource<out T>(val status: ViewStatus, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ViewStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(ViewStatus.ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(ViewStatus.LOADING, null, null)
        }

    }
}