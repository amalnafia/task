package com.example.movietask.application

import android.app.Application
import com.example.task.di.ApplicationComponent
import com.example.task.di.DaggerApplicationComponent

class MyApplication : Application() {
    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}