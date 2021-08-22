package com.example.task.di

import com.example.task.repo.ItemsRepo
import com.example.task.ui.download.ItemsAdapter
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
    fun provideItemsRepo(): ItemsRepo {
        return ItemsRepo(provideNetworkModule().provideNetworkInterface())
    }

    @Provides
    fun provideMainAdapter(): ItemsAdapter {
        return ItemsAdapter()
    }

}