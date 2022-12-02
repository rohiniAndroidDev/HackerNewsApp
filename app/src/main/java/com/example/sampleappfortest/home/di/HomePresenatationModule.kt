package com.example.sampleappfortest.home.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleappfortest.common.ViewModelFactory
import com.example.sampleappfortest.di.ViewModelKey
import com.example.sampleappfortest.home.presentation.ui.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomePresentationModule {
    @Binds
    abstract fun bindsViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(mHomeViewModel: HomeViewModel): ViewModel


}