package com.example.sampleappfortest.home.di

import com.example.sampleappfortest.db.AppDatabase
import com.example.sampleappfortest.home.data.dao.IdDao
import com.example.sampleappfortest.home.data.dao.NewsDao
import com.example.sampleappfortest.home.data.sources.HomeLocalDataSource
import com.example.sampleappfortest.home.data.sources.HomeLocalDataSourceImpl
import com.example.sampleappfortest.home.data.sources.HomeRemoteDataSource
import com.example.sampleappfortest.home.data.sources.HomeRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [HomeRemoteModule.Binders::class])
class HomeRemoteModule {
    @Module
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: HomeRemoteDataSourceImpl
        ): HomeRemoteDataSource
        @Binds
        fun bindsLocalSource(
            localDataSourceImpl: HomeLocalDataSourceImpl
        ): HomeLocalDataSource
    }

    @Provides
    fun provideIDDao(db: AppDatabase): IdDao = db.idsDao()
    @Provides
    fun provideNewsDao(db: AppDatabase): NewsDao = db.newsDao()




}