package com.example.jobreadinesschallenge.api

sealed class APIResult<T> {

    data class Success<T>(val result: T) : APIResult<T>()

    data class Error<T>(val err: Throwable) : APIResult<T>()
}