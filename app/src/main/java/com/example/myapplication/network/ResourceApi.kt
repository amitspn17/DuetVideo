package com.example.myapplication.network

sealed class ResourceApi<out R> {
    data class Success<out T>(val data: T) : ResourceApi<T>()
    data class Error(val exception: Exception) : ResourceApi<Nothing>()
    object Loading : ResourceApi<Nothing>()

}