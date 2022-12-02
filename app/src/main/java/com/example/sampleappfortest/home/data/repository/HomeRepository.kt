package com.example.sampleappfortest.home.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.sampleappfortest.common.ApiResult
import com.example.sampleappfortest.common.Result
import com.example.sampleappfortest.home.model.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getProfile(): LiveData<com.example.sampleappfortest.common.Result<ProfileDetails>>
    fun getImages(): LiveData<Result<List<ImageDetails>>>
//     suspend fun getTopNewsStoriesItemIds(): LiveData<Result<IdsResponse>>
    suspend fun getNewsStoriesItemIds(isRetry:Boolean): ApiResult<List<Int>>
     suspend fun getItemFromIds(): LiveData<Result<List<NewsItem>>>
     suspend fun getItemFromId(id: Int): ApiResult<NewsItem>

    suspend fun storeIdsInDb(list: List<IdModel>)
    suspend fun fetchIdsFromDb(): List<IdModel>
//    news related functions
 suspend fun fetchItemByIdFromLocalDb(): List<NewsItem>


 suspend fun storeItemInDb(item: NewsItem)
 fun getPaginatedFlow(idsList: List<Int>): Flow<PagingData<NewsItem>>

 suspend fun getNewsByID(id: Int):NewsItem?
}
