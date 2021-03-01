package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.response.VideoData

@Dao
interface UploadVideoDao {
    @Query("SELECT * FROM video_table")
    suspend fun getAllVideos():MutableList<VideoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addVideo(category:MutableList<VideoData>)
}