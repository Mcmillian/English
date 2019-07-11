package com.mcmillian.english

import android.app.Application
import com.mcmillian.english.model.local.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
    }
}