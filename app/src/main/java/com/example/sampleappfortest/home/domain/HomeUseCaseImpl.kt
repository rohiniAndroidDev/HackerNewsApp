package com.example.sampleappfortest.home.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.sampleappfortest.common.ApiResult
import com.example.sampleappfortest.common.Result
import com.example.sampleappfortest.home.data.repository.HomeRepository
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ProfileDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(var mRepository: HomeRepository) : HomeUseCase {
    override suspend fun getProfile(): LiveData<com.example.sampleappfortest.common.Result<ProfileDetails>> {
        return mRepository.getProfile()
    }


    override suspend fun getItemFromId(): LiveData<Result<List<NewsItem>>> {
        return mRepository.getItemFromIds()
    }

    override fun invoke(idsList: List<Int>): Flow<PagingData<NewsItem>> {
        return mRepository.getPaginatedFlow(idsList)
    }

    override suspend fun invoke(isretry: Boolean): ApiResult<List<Int>> {
        return mRepository.getNewsStoriesItemIds(isretry)
    }


}