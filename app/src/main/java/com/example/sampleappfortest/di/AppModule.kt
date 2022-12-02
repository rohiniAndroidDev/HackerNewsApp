package com.example.sampleappfortest.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.sampleappfortest.db.AppDatabase
import com.example.sampleappfortest.home.data.dao.IdDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,AppDatabase::class.java,"testDatabase.db")
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration().build()
    }

}