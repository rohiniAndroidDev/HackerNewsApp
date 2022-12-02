package com.example.sampleappfortest.di

import com.example.sampleappfortest.common.BASE_URL
import com.example.sampleappfortest.di.qualifiers.IO
import com.example.sampleappfortest.di.qualifiers.MainThread
import com.example.sampleappfortest.home.data.service.HomeService
import com.example.sampleappfortest.home.data.sources.GsonInstance
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

@Module
class NetworkModule {


   /* @Provides
    fun providesGson(): Gson = Gson()*/
    @Provides
    fun providesGson() = GsonInstance.instance()
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .build()


    @IO
    @Provides
    fun providesIoDispatcher(): CoroutineContext = Dispatchers.IO

    @MainThread
    @Provides
    fun providesMainThreadDispatcher(): CoroutineContext = Dispatchers.Main

    private fun provideOkHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient().newBuilder()
        client.connectTimeout(60, TimeUnit.SECONDS)
        client.readTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            chain.proceed(newRequest)
        }
        client.addInterceptor(logging)

        return client.build()
    }

    @Provides
    fun providesHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)



}