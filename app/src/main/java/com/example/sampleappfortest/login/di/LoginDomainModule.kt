package com.example.sampleappfortest.login.di


import com.example.sampleappfortest.login.data.repository.LoginRepository
import com.example.sampleappfortest.login.data.repository.LoginRepositoryImpl
import com.example.sampleappfortest.login.domain.LoginUseCase
import com.example.sampleappfortest.login.domain.LoginUsecaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LoginDomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindsLoginUseCase(
        mLoginUseCase: LoginUsecaseImpl
    ): LoginUseCase
}