package com.example.soala13pam

import android.app.Application
import com.example.soala13pam.container.AppContainer
import com.example.soala13pam.container.Container

class EventApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}