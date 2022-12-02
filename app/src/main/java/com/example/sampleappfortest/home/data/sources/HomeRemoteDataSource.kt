package com.example.sampleappfortest.home.data.sources

import com.example.sampleappfortest.common.ApiResult
import com.example.sampleappfortest.home.data.network.ResponseWrapper
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.ImageDetails
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ProfileDetails
import retrofit2.Response
import retrofit2.http.Path

interface HomeRemoteDataSource {
    suspend fun getProfile(): ProfileDetails

    /*news related functions*/
    suspend fun getTopNewsStoriesItemIds(): ApiResult<IdsResponse>
    suspend fun getTopNewsStoriesItemIdsApiCall(): ResponseWrapper<IdsResponse>
    suspend fun getItemFromIdApiCall(id: Int): ResponseWrapper<NewsItem?>
    suspend fun getItemFromId(id: Int): ApiResult<NewsItem>
    suspend fun getItemForId(id: Int): NewsItem


}
