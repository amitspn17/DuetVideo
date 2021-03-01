package com.example.myapplication.ui

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.myapplication.util.ObservableViewModel
import java.util.*

class VideoUri: ObservableViewModel() {

    val areInputsReady = MediatorLiveData<Boolean>()
    val fileUri = MutableLiveData<Uri>(null)
    val imageReady = Transformations.map(fileUri) { it != null }

    init {
        areInputsReady.addSource(imageReady) { areInputsReady.value = checkInputs() }
    }

    private fun checkInputs(): Boolean {
        return !(imageReady.value == false)
    }
}