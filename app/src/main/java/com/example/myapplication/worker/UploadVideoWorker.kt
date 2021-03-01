package com.example.myapplication.worker

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.myapplication.R
import com.example.myapplication.db.UploadVideoDao
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.ResourceApi
import com.example.myapplication.repository.VideoRepository
import com.example.myapplication.ui.activity.VideoListActivity
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UploadVideoWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: VideoRepository
) : CoroutineWorker(context, workerParams) {

    private val context = applicationContext


    override suspend fun doWork(): Result {
        val fileUri = inputData.getString(KEY_IMAGE_URI)?.toUri()
        fileUri?.let { return uploadImageFromURI(it) }
        throw IllegalStateException("Image URI doesn't exist")
    }

    @SuppressLint("ResourceType")
    private suspend fun uploadImageFromURI(fileUri: Uri): Result = suspendCoroutine { cont ->
        repository.uploadImageWithUri(fileUri) { result, percentage ->
            when (result) {
                is ResourceApi.Success -> {
                   // showUploadFinishedNotification(result.data)

                    val data = Data.Builder()
                        .putAll(inputData)
                        .putString(KEY_UPLOADED_URI, result.data.toString())

                    cont.resume(Result.success(data.build()))
                }
                is ResourceApi.Loading -> {
                }
                is ResourceApi.Error -> {
                    cont.resume(Result.failure())
                }
            }
        }
    }



    companion object {
        const val KEY_IMAGE_URI: String = "key-image-uri"
        const val KEY_UPLOADED_URI: String = "key-uploaded-uri"
    }
}