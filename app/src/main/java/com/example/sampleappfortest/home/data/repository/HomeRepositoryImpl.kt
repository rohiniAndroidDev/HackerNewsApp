package com.example.sampleappfortest.home.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.example.sampleappfortest.common.*
import com.example.sampleappfortest.home.data.network.ResponseWrapper
import com.example.sampleappfortest.home.data.sources.HomeLocalDataSource
import com.example.sampleappfortest.home.data.sources.HomeRemoteDataSource
import com.example.sampleappfortest.home.model.*
import com.example.sampleappfortest.home.presentation.ui.paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(var mRemoteDataSource: HomeRemoteDataSource,var mLocalDataSource: HomeLocalDataSource) :
    HomeRepository {
    val idsList = IdsResponse()

    override fun getProfile(): LiveData<com.example.sampleappfortest.common.Result<ProfileDetails>> {
        return liveData {
            emit(com.example.sampleappfortest.common.Result.loading())
            try {
                val response = mRemoteDataSource.getProfile()
                emit(com.example.sampleappfortest.common.Result.success(response))

            } catch (exception: Exception) {
                emit(
                    com.example.sampleappfortest.common.Result.error(
                        exception.message ?: "",
                        null
                    )
                )
            }
        }
    }

    override fun getImages(): LiveData<Result<List<ImageDetails>>> {
        return liveData {
            emit(com.example.sampleappfortest.common.Result.loading())
            try {
                val response = mRemoteDataSource.getImages()
                emit(com.example.sampleappfortest.common.Result.success(response))

            } catch (exception: Exception) {
                emit(
                    com.example.sampleappfortest.common.Result.error(
                        exception.message ?: "",
                        null
                    )
                )
            }
        }
    }

    override suspend fun getNewsStoriesItemIds(isRetry: Boolean): ApiResult<List<Int>> {
        val networkResponse = if (AppCache.canFetch("Top")) {
            mRemoteDataSource.getTopNewsStoriesItemIds()
        } else {
            ApiResult.OK("", IdsResponse())
        }
        val localResponse: List<IdModel> = fetchIdsFromDb()

        val idsList = IdsResponse()

        localResponse.forEach { item ->
            idsList.add(item.id)
        }

        if (networkResponse.success && networkResponse.result != null) {
            val list = mutableListOf<IdModel>()
            val currentTimestamp = System.currentTimeMillis()
            for (i in networkResponse.result) {
                idsList.add(i)
                list.add(IdModel(i,  currentTimestamp))
            }
            storeIdsInDb(list)
            return ApiResult.OK(networkResponse.message, idsList)
        }

        return if (idsList.size == 0) {
            ApiResult.ERROR(networkResponse.message)
        } else {
            ApiResult.OK("Fetched data from cache", idsList)
        }
    }






    override suspend fun getItemFromIds(): LiveData<Result<List<NewsItem>>> {
        return liveData {
            emit(com.example.sampleappfortest.common.Result.loading())
            try {
                var responseList= mutableListOf<NewsItem>()

//                todo need to fix this
                   /* val newslist=fetchItemByIdFromLocalDb().toMutableList()
                    newslist?.forEach {
                        responseList.add(it)
                    }*/
                //now search only works for 20 items
                    if(responseList.isEmpty()) {
                        val localResponse: List<IdModel> = fetchIdsFromDb()

                        val idsList = IdsResponse()

                        localResponse.forEach { item ->
                            idsList.add(item.id)
                        }
                        for (i in idsList.take(20)) {
                            val response = mRemoteDataSource.getItemForId(i)
                            storeItemInDb(response)
                            responseList.add(response)
                        }
                    }
                    emit(com.example.sampleappfortest.common.Result.success(responseList))


            } catch (exception: Exception) {
                emit(
                    com.example.sampleappfortest.common.Result.error(
                        exception.message ?: "",
                        null
                    )
                )
            }
        }
    }

    override suspend fun getItemFromId(id: Int):  ApiResult<NewsItem> {
//            emit(com.example.sampleappfortest.common.Result.loading())
        val fromLocal = getNewsByID(id)
        if (fromLocal != null) {
            return ApiResult.OK(res = fromLocal)
        }

        val fromNetwork = mRemoteDataSource.getItemFromId(id)
        if (fromNetwork.success)
            storeItemInDb(fromNetwork.result!!)

        return fromNetwork
        }


    override suspend fun storeIdsInDb(list: List<IdModel>) {
        mLocalDataSource.putIds(list)
    }

    override suspend fun fetchIdsFromDb(): List<IdModel> {
        return mLocalDataSource.getNewsIds()
    }


    override suspend fun fetchItemByIdFromLocalDb(): List<NewsItem> {
        return mLocalDataSource.getNewsList()
    }


    override suspend fun storeItemInDb(item: NewsItem) {
        mLocalDataSource.storeItemInDb(item)
    }

    override fun getPaginatedFlow(idsList: List<Int>): Flow<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS_LIMIT,
                maxSize = MAX_PAGE_SIZE,
                prefetchDistance = PRE_FETCH_DISTANCE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(::getItemFromId, idsList) }
        ).flow
    }

    override suspend fun getNewsByID(id: Int): NewsItem? {
      return mLocalDataSource.getNewsById(id)
    }

}