package com.example.sampleappfortest.di

import com.example.sampleappfortest.home.presentation.ui.fragments.HomeFragment
import com.example.sampleappfortest.home.presentation.ui.fragments.ProfileFragment
import com.example.sampleappfortest.home.presentation.ui.fragments.SearchFragment
import com.example.sampleappfortest.login.presentation.fragments.LoginFragment
import com.example.sampleappfortest.login.presentation.fragments.OtpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeOTPFragment(): OtpFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

      @ContributesAndroidInjector
      abstract fun contributeSearchFragment(): SearchFragment
}