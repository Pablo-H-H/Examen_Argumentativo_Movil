package com.example.kotlin.historia.utils


import android.app.Application
import com.example.kotlin.historia.data.network.NetworkModuleDI

class Historias : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkModuleDI.initializeParse(this)
    }
}