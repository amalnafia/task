package com.example.task.util

sealed class Result<out T : Any>
class Success<out T : Any>(val response: T) : Result<T>()
class Fail(val exception: Exception) : Result<Nothing>()