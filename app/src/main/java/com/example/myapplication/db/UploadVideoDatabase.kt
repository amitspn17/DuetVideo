package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.response.VideoData
import com.example.myapplication.util.ImageBitmapString

@Database(entities = arrayOf(VideoData::class),version = 1,exportSchema = false)
@TypeConverters(ImageBitmapString::class)
abstract class UploadVideoDatabase: RoomDatabase() {
    abstract fun uploadVideoDao():UploadVideoDao
}