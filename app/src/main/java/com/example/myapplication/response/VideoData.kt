package com.example.myapplication.response

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "video_table")
data class VideoData(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id:Int?=null,
        @ColumnInfo(name = "video_url")
    val video_url: Bitmap?=null,
        @ColumnInfo(name = "video_image")
    val video_image: String
) {
}