package com.example.sampleappfortest.di

import com.example.sampleappfortest.home.presentation.ui.activities.HomeActivity
import com.example.sampleappfortest.login.presentation.activities.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributesLoginActivity():LoginActivity


}