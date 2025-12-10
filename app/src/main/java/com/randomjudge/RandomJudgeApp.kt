package com.randomjudge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RandomJudgeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the app
    }
}