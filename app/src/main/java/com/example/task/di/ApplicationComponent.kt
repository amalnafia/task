package com.example.task.di

import android.content.Context
import com.example.task.di.AppModule
import com.example.task.di.NetworkModule
import com.example.task.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(main: MainActivity)
}