package com.example.myapplication.repository

import android.net.Uri
import com.example.myapplication.db.UploadVideoDao
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.ResourceApi
import com.example.myapplication.response.VideoData
import com.example.myapplication.response.VideoResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Response
import javax.annotation.Resource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class VideoRepository @Inject constructor(
        private  val api:ApiService,
        private val database: UploadVideoDao
):ApiService {
    private val db: FirebaseFirestore = Firebase.firestore
    private val storage = FirebaseStorage.getInstance().reference
    val settings = FirebaseFirestoreSettings.Builder()
        .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
        .build()
   override  suspend fun getVideos(): ResourceApi<List<VideoData>> =
            suspendCoroutine { cont ->
                db.collection("video")
                        .get()
                        .addOnSuccessListener {
                            try {
                                cont.resume(ResourceApi.Success(it.toObjects()))
                            } catch (e: Exception) {
                                cont.resume(ResourceApi.Error(e))
                            }
                        }.addOnFailureListener {
                            cont.resume(ResourceApi.Error(it))
                        }
            }

    override suspend fun newVideos(video: VideoData): ResourceApi<Boolean> =
            suspendCoroutine { cont ->
                db.collection("video")
                        .add(video)
                        .addOnSuccessListener {
                            cont.resume(ResourceApi.Success(true))
                        }.addOnFailureListener {
                            cont.resume(ResourceApi.Error(it))
                        }
            }

    override fun uploadImageWithUri(uri: Uri, block: ((ResourceApi<Uri>, Int) -> Unit)?) {
        val photoRef = storage.child("video").child(uri.lastPathSegment!!)
        photoRef.putFile(uri)
                .addOnProgressListener { taskSnapshot ->
                    val percentComplete = if (taskSnapshot.totalByteCount > 0) {
                        (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                    } else 0

                    block?.invoke(ResourceApi.Loading, percentComplete)
                }.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    photoRef.downloadUrl
                }
                .addOnSuccessListener { block?.invoke(ResourceApi.Success(it), 100) }
                .addOnFailureListener { block?.invoke(ResourceApi.Error(it), 0) }
    }


}
