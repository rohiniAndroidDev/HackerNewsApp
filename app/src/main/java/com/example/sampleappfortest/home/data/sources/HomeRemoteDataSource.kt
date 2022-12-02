package com.example.sampleappfortest.home.data.sources

import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.ImageDetails
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ProfileDetails
import retrofit2.http.Path

interface HomeRemoteDataSource {
    suspend fun getProfile(): ProfileDetails
    suspend fun getImages(): List<ImageDetails>

    /*news related functions*/
    suspend fun getTopNewsStoriesItemIds(): IdsResponse
    suspend fun getItemFromId(id: Int): NewsItem


}
