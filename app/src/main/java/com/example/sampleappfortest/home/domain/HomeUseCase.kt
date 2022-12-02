package com.example.sampleappfortest.home.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ProfileDetails
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    suspend fun getProfile(): LiveData<com.example.sampleappfortest.common.Result<ProfileDetails>>
    suspend fun getItemFromId(): LiveData<com.example.sampleappfortest.common.Result<List<NewsItem>>>
    fun invoke(idsList: List<Int>): Flow<PagingData<NewsItem>>
    suspend fun invoke(isRetry: Boolean): com.example.sampleappfortest.common.ApiResult<List<Int>>
}
