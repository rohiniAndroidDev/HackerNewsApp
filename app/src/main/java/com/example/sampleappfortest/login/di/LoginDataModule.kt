package com.example.sampleappfortest.login.di

import com.example.sampleappfortest.db.AppDatabase
import com.example.sampleappfortest.login.data.dao.LoginDao
import com.example.sampleappfortest.login.data.services.LoginService
import com.example.sampleappfortest.login.data.sources.LoginLocalDataSource
import com.example.sampleappfortest.login.data.sources.LoginLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [LoginDataModule.Binders::class])
class LoginDataModule {
    @Module
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: LoginLocalDataSourceImpl
        ): LoginLocalDataSource
    }

    @Provides
    fun providesLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    internal fun provideLoginDao(database: AppDatabase):LoginDao{
        return database.loginDao()
    }


}