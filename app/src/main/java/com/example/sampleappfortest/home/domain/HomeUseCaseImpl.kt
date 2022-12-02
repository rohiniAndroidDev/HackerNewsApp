package com.example.sampleappfortest.home.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.sampleappfortest.common.Result
import com.example.sampleappfortest.home.data.repository.HomeRepository
import com.example.sampleappfortest.home.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(var mRepository: HomeRepository) : HomeUseCase {
    override suspend fun getProfile(): LiveData<com.example.sampleappfortest.common.Result<ProfileDetails>> {
        return mRepository.getProfile()
    }

    override suspend fun getImages(): LiveData<Result<List<ImageDetails>>> {
        return mRepository.getImages()
    }

    override suspend fun getTopNewsStoriesItemIds(): LiveData<Result<IdsResponse>> {
        return mRepository.getTopNewsStoriesItemIds()
    }

    override suspend fun getItemFromId(id: IdsResponse): LiveData<Result<ArrayList<NewsItem>>> {
        return mRepository.getItemFromIds(id)
    }

    override fun invoke(idsList: List<Int>): Flow<PagingData<NewsItem>> {
            return mRepository.getPaginatedFlow(idsList)
    }

    override suspend fun invoke(isretry:Boolean): Result<List<Int>> {
        return mRepository.getNewsStoriesItemIds(isretry)
    }




}