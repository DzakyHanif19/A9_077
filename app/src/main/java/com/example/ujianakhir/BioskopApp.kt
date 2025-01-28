package com.example.ujianakhir

import android.app.Application
import com.example.ujianakhir.di.AppContainer
import com.example.ujianakhir.di.BioskopContainerApp

class BioskopApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Inisialisasi container DI
        container = BioskopContainerApp()
    }
}