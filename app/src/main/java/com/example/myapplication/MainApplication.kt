package com.example.myapplication

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true)
    }
}