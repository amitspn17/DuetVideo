package com.example.myapplication.network

import com.example.myapplication.util.Constant.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitInstance {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create( ))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService{
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
}
