package com.example.sampleappfortest.home.data.service

import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.ImageDetails
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ProfileDetails
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET("User")
    fun getProfile(): Deferred<Response<ProfileDetails>>

    @GET("images")
    fun getImages(): Deferred<Response<List<ImageDetails>>>

    @GET("topstories.json")
     fun getTopNewsStoriesItemIds(): Deferred<Response<IdsResponse>>

    @GET("item/{id}.json")
     fun getItemFromId(@Path("id") id: Int): Deferred<Response<NewsItem>>


}
