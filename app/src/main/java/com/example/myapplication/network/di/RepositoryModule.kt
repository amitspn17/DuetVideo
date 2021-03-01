package com.example.myapplication.network.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkerParameters
import com.example.myapplication.db.UploadVideoDatabase
import com.example.myapplication.network.ApiService
import com.example.myapplication.repository.VideoRepository
import com.example.myapplication.worker.UploadVideoWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

  /*  @Singleton
    @Provides
    fun provideVideoRepository(
        apiService: ApiService
    ): VideoRepository{
        return VideoRepository(apiService)
    }*/

    @Singleton
    @Provides
    fun  provideVideoDatabase(
            @ApplicationContext app: Context

    )= Room.databaseBuilder(
            app,
            UploadVideoDatabase::class.java,
            "video_db"
    ).build()


    @Provides
    fun provideVideoRepository(
            apiService: ApiService,
            database: UploadVideoDatabase
    ): VideoRepository{
        return VideoRepository(apiService,database.uploadVideoDao())
    }

    @Provides
    fun provideUploadWorker(
            @ApplicationContext app: Context,
            apiService: ApiService,
            workerParams: WorkerParameters,
            repository: VideoRepository
    ): UploadVideoWorker{
        return UploadVideoWorker(app,workerParams,repository)
    }
}