package com.example.sampleappfortest.home.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.example.sampleappfortest.common.*
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

    override fun getTopNewsStoriesItemIds(): LiveData<Result<IdsResponse>> {
        return liveData {
            emit(com.example.sampleappfortest.common.Result.loading())
            try {

                val response = mRemoteDataSource.getTopNewsStoriesItemIds()
                if(response.isNullOrEmpty())
                {
                    val localResponse: List<IdModel> = fetchIdsFromDb()
                    localResponse.forEach { item ->
                        idsList.add(item.id)
                    }
                }else {
                    val list = mutableListOf<IdModel>()
                    val currentTimestamp = System.currentTimeMillis()
                    for (i in response) {
                        idsList.add(i)
                        list.add(IdModel(i, currentTimestamp))
                    }
                    storeIdsInDb(list)
                }
                emit(com.example.sampleappfortest.common.Result.success(idsList))

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

override suspend fun getNewsStoriesItemIds(isretry:Boolean): Result<List<Int>> {
    val networkResponse = if(!isretry) {
       if (AppCache.canFetch("Top")) {
            Result.success(mRemoteDataSource.getTopNewsStoriesItemIds())
        } else {
            Result.success(IdsResponse())
        }
    }else
    {
        Result.success(mRemoteDataSource.getTopNewsStoriesItemIds())
    }
        val localResponse: List<IdModel> = fetchIdsFromDb()

        val idsList = mutableSetOf<Int>()

        localResponse.forEach { item ->
            idsList.add(item.id)
        }

        if (networkResponse.status==Status.SUCCESS && !networkResponse.data.isNullOrEmpty()) {
            val list = mutableListOf<IdModel>()
            val currentTimestamp = System.currentTimeMillis()
            for (i in networkResponse.data) {
                idsList.add(i)
                list.add(IdModel(i, currentTimestamp))
            }
            storeIdsInDb(list)
            return Result.success(idsList.toList())
        }

        return if (idsList.size == 0) {
            Result.error("demo",null)
        } else {
            Result.success(idsList.toList())
        }
    }


    override suspend fun getItemFromIds(id: IdsResponse): LiveData<Result<ArrayList<NewsItem>>> {
        return liveData {
            emit(com.example.sampleappfortest.common.Result.loading())
            try {
                var responseList= arrayListOf<NewsItem>()

                if(id.isEmpty())
                {
                    emit(com.example.sampleappfortest.common.Result.success(responseList))

                }else
                {
                    for(i in id) {
                        val response = mRemoteDataSource.getItemFromId(i)
                        storeItemInDb(response)
                        responseList.add(response)
                    }
                    emit(com.example.sampleappfortest.common.Result.success(responseList))

                }

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

    override suspend fun getItemFromId(id: Int): Result<NewsItem> {
//            emit(com.example.sampleappfortest.common.Result.loading())
            try {
                var responseList= arrayListOf<NewsItem>()

                        val response = mRemoteDataSource.getItemFromId(id)
                        storeItemInDb(response)
                        responseList.add(response)
                   return  com.example.sampleappfortest.common.Result.success(response)


            } catch (exception: Exception) {
                return com.example.sampleappfortest.common.Result.error(
                        exception.message ?: "",
                        null
                    )

            }
    }

    override suspend fun storeIdsInDb(list: List<IdModel>) {
        mLocalDataSource.putIds(list)
    }

    override suspend fun fetchIdsFromDb(): List<IdModel> {
        return mLocalDataSource.getNewsIds()
    }


    override suspend fun fetchItemByIdFromLocalDb(): List<NewsItem>? {
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

}