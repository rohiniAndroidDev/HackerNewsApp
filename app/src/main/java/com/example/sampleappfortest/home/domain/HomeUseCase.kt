package com.example.sampleappfortest.home.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.sampleappfortest.home.model.*
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    suspend fun getProfile(): LiveData<com.example.sampleappfortest.common.Result<ProfileDetails>>
    suspend fun getImages(): LiveData<com.example.sampleappfortest.common.Result<List<ImageDetails>>>
    suspend fun getTopNewsStoriesItemIds(): LiveData<com.example.sampleappfortest.common.Result<IdsResponse>>
    suspend fun getItemFromId(id: IdsResponse): LiveData<com.example.sampleappfortest.common.Result<ArrayList<NewsItem>>>

    fun invoke(idsList: List<Int>): Flow<PagingData<NewsItem>>
    suspend fun invoke(isRetry:Boolean): com.example.sampleappfortest.common.Result<List<Int>>
}
