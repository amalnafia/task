package com.example.task.di

import com.example.task.repo.MainRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideNetworkModule(): NetworkModule {
        return NetworkModule()
    }

    @Provides
    @Singleton
    fun provideMainRepo(): MainRepo {
        return MainRepo(provideNetworkModule().provideNetworkInterface())
    }
}