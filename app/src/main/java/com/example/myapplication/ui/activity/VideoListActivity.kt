package com.example.myapplication.ui.activity;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityVideoListBinding
import com.example.myapplication.network.ResourceApi
import com.example.myapplication.ui.adapters.VideoListAdapter
import com.example.myapplication.ui.viewmodels.VideoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreSettings

import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.android.synthetic.main.activity_video_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import com.example.myapplication.ui.adapters.VideoListAdapter.ClickListener

@AndroidEntryPoint
 class VideoListActivity : AppCompatActivity(),ClickListener {
    private val videoViewModel: VideoViewModel by viewModels()
    val activityScope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding:ActivityVideoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_list)
        Timber.d("current user :"+ FirebaseAuth.getInstance().currentUser)

        val adapter  = VideoListAdapter()
        binding.categoryRecycler.adapter = adapter
        binding.tvUpload.setOnClickListener {
            navigateToUpload()
        }
       // subscribeUi(adapter)
        }



    private fun subscribeUi(adapter: VideoListAdapter) {
        videoViewModel.loadVideos().observe(this, Observer { response ->
                when(response) {
                    is ResourceApi.Success -> {
                        hideProgressBar()
                        response.data?.let { newResponse ->
                            adapter.submitList(newResponse)
                        }
                    }
                    is ResourceApi.Loading ->{
                        showProgressBar()
                    }
                    is ResourceApi.Error ->{
                        hideProgressBar()
                    }
                }

            })
    }


    private fun hideProgressBar(){
        ProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        ProgressBar.visibility = View.VISIBLE
    }
    fun navigateToUpload(){
        var  intent = Intent(this,UploadVideo::class.java)
        startActivity(intent)
    }

    override fun onclick(view: View, position: Int) {
       navigateToUpload()
    }
}
