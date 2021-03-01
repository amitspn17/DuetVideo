package com.example.myapplication.network

import android.net.Uri
import com.example.myapplication.response.VideoData
import com.example.myapplication.response.VideoResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

   /* @GET("splash-image")
    suspend fun  getVideoList():Response<VideoResponse>*/

    suspend fun getVideos(): ResourceApi<List<VideoData>>

    suspend fun newVideos(deal: VideoData): ResourceApi<Boolean>

    fun uploadImageWithUri(uri: Uri, block: ((ResourceApi<Uri>, Int) -> Unit)? = null)


}