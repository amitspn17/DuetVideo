package com.example.myapplication.response

import com.squareup.moshi.Json

data class VideoResponse(
    @field: Json(name ="video_data")
    val video_name:MutableList<VideoData>
) {
}