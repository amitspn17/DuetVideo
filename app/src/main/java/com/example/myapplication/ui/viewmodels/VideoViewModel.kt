package com.example.myapplication.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.myapplication.network.ResourceApi
import com.example.myapplication.repository.VideoRepository
import com.example.myapplication.response.VideoData
import com.example.myapplication.response.VideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class VideoViewModel @ViewModelInject constructor(
    private val videoRepository: VideoRepository
) :ViewModel() {
    private val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO
    val videoList: MutableLiveData<ResourceApi<VideoResponse>> = MutableLiveData()
    var videoResponse: VideoResponse? = null
  /*  init {
        getVideoResponse()
    }*/


    fun loadVideos(): LiveData<ResourceApi<List<VideoData>>> = liveData(coroutineContext) {
        emit(ResourceApi.Loading)

        val result = videoRepository.getVideos()
        if (result is ResourceApi.Success && result.data.isEmpty())
            emit(ResourceApi.Error(Exception("There is no deals at the moment.\n Wait for it.")))
        else
            emit(result)
    }

    fun newDeal(video: VideoData): LiveData<ResourceApi<Boolean>> = liveData(coroutineContext) {
        emit(ResourceApi.Loading)
        emit(videoRepository.newVideos(video))
    }


  /*  fun getVideoResponse() = viewModelScope.launch {
        videoList.postValue(ResourceApi.Loading)
        val response = videoRepository.getVideoList()
        videoList.postValue(handleVideoResponse(response))
    }


    private fun handleVideoResponse(response: Response<VideoResponse>): ResourceApi<VideoResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                videoResponse = resultResponse
                return ResourceApi.Success(videoResponse ?: resultResponse)
            }

        }
        return ResourceApi.Error(response.message())
    }*/
}
