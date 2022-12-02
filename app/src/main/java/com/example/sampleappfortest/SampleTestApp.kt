package com.example.sampleappfortest

import com.example.sampleappfortest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SampleTestApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

    }


    companion object {
        private val TAG = SampleTestApp::class.java.simpleName


        @get:Synchronized
        var instance: SampleTestApp? = null
            private set
    }


}
