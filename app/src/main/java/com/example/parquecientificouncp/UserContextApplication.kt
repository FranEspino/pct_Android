package com.example.parquecientificouncp

import android.app.Application
import com.example.parquecientificouncp.storage.SharedPreferenceContext

class UserContextApplication: Application() {
    companion object {
        lateinit var context :SharedPreferenceContext
    }
    override fun onCreate() {
        super.onCreate()
        context = SharedPreferenceContext(applicationContext)
    }
}