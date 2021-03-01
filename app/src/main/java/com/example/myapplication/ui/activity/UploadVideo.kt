package com.example.myapplication.ui.activity;

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.work.*

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityUploadVideoBinding
import com.example.myapplication.worker.UploadVideoWorker
import timber.log.Timber

class UploadVideo : AppCompatActivity (){

     private lateinit var binding:ActivityUploadVideoBinding
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this,R.layout.activity_upload_video)
         launchGallery()
     }

    private fun launchGallery() {
        // Pick an image from storage
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, RC_TAKE_PICTURE)
    }

    private fun uploadVideo( url:String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadImageWorker: OneTimeWorkRequest = OneTimeWorkRequestBuilder<UploadVideoWorker>()
            .setConstraints(constraints)
            .setInputData(
                workDataOf(

                    UploadVideoWorker.KEY_IMAGE_URI to url)
            )
            .build()


        WorkManager.getInstance().let { manager ->
            manager
                .beginWith(uploadImageWorker)
                .enqueue()
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("onActivityResult:$requestCode:$resultCode:$data")
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                binding.videoModel?.fileUri?.value = data?.data
                val bitmap = BitmapFactory.decodeStream(data?.data?.let { getContentResolver().openInputStream(it) })
                uploadVideo(data?.data.toString())

            } else {
                //toast("Picking picture failed.")
            }
        }
    }

    companion object {
        private const val RC_TAKE_PICTURE = 233
    }
}