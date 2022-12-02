package com.example.sampleappfortest.home.di

import com.example.sampleappfortest.home.data.repository.HomeRepository
import com.example.sampleappfortest.home.data.repository.HomeRepositoryImpl
import com.example.sampleappfortest.home.domain.HomeUseCase
import com.example.sampleappfortest.home.domain.HomeUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class HomeDomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun bindsHomeUseCase(
        mHomeUseCase: HomeUseCaseImpl
    ): HomeUseCase
}