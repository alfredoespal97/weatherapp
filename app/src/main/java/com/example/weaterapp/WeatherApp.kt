package com.example.weaterapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import coil.ImageLoader

@HiltAndroidApp
class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}