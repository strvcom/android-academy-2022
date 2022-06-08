package com.strv.movies

import android.app.Application
import com.strv.com.plantCrashReportingTree
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        plantCrashReportingTree()
    }
}