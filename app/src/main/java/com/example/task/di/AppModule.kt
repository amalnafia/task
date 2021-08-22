package com.example.task.di

import com.example.task.repo.DownloadRepo
import com.example.task.ui.download.DownloadAdapter
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
    fun provideDownloadRepo(): DownloadRepo {
        return DownloadRepo(provideNetworkModule().provideNetworkInterface())
    }
    @Provides
    fun provideMainAdapter(): DownloadAdapter {
        return DownloadAdapter()
    }

}