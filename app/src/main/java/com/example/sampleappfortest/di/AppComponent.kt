package com.example.sampleappfortest.di

import android.app.Application
import com.example.sampleappfortest.SampleTestApp
import com.example.sampleappfortest.home.di.HomeDomainModule
import com.example.sampleappfortest.home.di.HomePresentationModule
import com.example.sampleappfortest.home.di.HomeRemoteModule
import com.example.sampleappfortest.login.di.LoginDomainModule
import com.example.sampleappfortest.login.di.LoginPresentationModule
import com.example.sampleappfortest.login.di.LoginDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, AppModule::class, NetworkModule::class, LoginPresentationModule::class, LoginDomainModule::class, LoginDataModule::class,
        ActivityBuilderModule::class, FragmentBuilderModule::class, HomeRemoteModule::class, HomePresentationModule::class, HomeDomainModule::class

    ]
)
interface AppComponent : AndroidInjector<SampleTestApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(app: SampleTestApp)
}