package com.yogigupta1206.m2ppokemon

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class M2PPokemonApp: Application() {

    companion object {
        private const val TAG = "M2PPokemonApp"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: M2PPokemonApp")
    }


}