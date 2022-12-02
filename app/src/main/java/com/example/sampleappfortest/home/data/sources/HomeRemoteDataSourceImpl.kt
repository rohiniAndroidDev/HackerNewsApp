package com.example.sampleappfortest.home.data.sources

import android.util.Log
import com.example.sampleappfortest.common.ApiResult
import com.example.sampleappfortest.common.RetrofitClientInstance
import com.example.sampleappfortest.di.qualifiers.IO
import com.example.sampleappfortest.home.data.network.ResponseWrapper
import com.example.sampleappfortest.home.data.service.HomeService
import com.example.sampleappfortest.home.model.*
import com.example.sampleappfortest.login.model.LoginResponse
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Converter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeRemoteDataSourceImpl @Inject constructor(
    var service: HomeService,
    @IO private val context: CoroutineContext
) : HomeRemoteDataSource {
    override suspend fun getProfile(): ProfileDetails =
        withContext(context) {
            val response = service.getProfile().await()
            if (response.isSuccessful) {
                response.body() ?: throw Exception("no response")
            } else if (response.errorBody() != null) {
                val converter: Converter<ResponseBody, LoginResponse> =
                    RetrofitClientInstance.retrofitInstance!!.responseBodyConverter(
                        ProfileDetails::class.java, arrayOfNulls<Annotation>(0)
                    )

                val error = converter.convert(response.errorBody())
                throw Exception(error?.message.toString())
            } else
                throw Exception(response.code().toString())
        }

    override suspend fun getImages(): List<ImageDetails> =
        withContext(context) {
            val response = service.getImages().await()
            if (response.isSuccessful) {
                response.body() ?: throw Exception("no response")
            } else if (response.errorBody() != null) {
                val converter: Converter<ResponseBody, LoginResponse> =
                    RetrofitClientInstance.retrofitInstance!!.responseBodyConverter(
                        ImageDetails::class.java, arrayOfNulls<Annotation>(0)
                    )

                val error = converter.convert(response.errorBody())
                throw Exception(error?.message.toString())
            } else
                throw Exception(response.code().toString())
        }

    override suspend fun getTopNewsStoriesItemIds(): ApiResult<IdsResponse> =withContext(context) {
        val idsList = IdsResponse()

        return@withContext when (
            val networkResponse = getTopNewsStoriesItemIdsApiCall()
        ) {
            is ResponseWrapper.GenericError ->
                ApiResult.ERROR(networkResponse.error + ", no data available in cached storage")
            ResponseWrapper.NetworkError ->
                ApiResult.NetworkError
            is ResponseWrapper.Success -> {
                run {
                    val list = mutableListOf<IdModel>()
                    val currentTimestamp = System.currentTimeMillis()
                    for (i in networkResponse.value) {
                        idsList.add(i)
                        list.add(IdModel(i, currentTimestamp))
                    }
                    ApiResult.OK("", idsList)
                }
            }
        }


        }


    override suspend fun getTopNewsStoriesItemIdsApiCall(): ResponseWrapper<IdsResponse> {
        return ResponseWrapper.safeApiCall {
            val response = service.getTopNewsStoriesItemIds().await()
            Log.d("sahdhsagdhjag", response?.isSuccessful.toString())

            when {
                response.isSuccessful -> {
                    response.body() ?: throw Exception("no response")
                }
                response.errorBody() != null -> {
                    val converter: Converter<ResponseBody, IdsResponse> =
                        RetrofitClientInstance.retrofitInstance!!.responseBodyConverter(

                            Long::class.java, arrayOfNulls<Annotation>(0)
                        )

                    val error = converter.convert(response.errorBody())
                    throw Exception(error?.toString())
                }
                else -> throw Exception(response.code().toString())
            }
        }
    }

    override suspend fun getItemFromIdApiCall(id:Int): ResponseWrapper<NewsItem?> {
        return ResponseWrapper.safeApiCall {
            val response = service.getItemFromId(id)
            Log.d("sahdhsagdhjag12", response?.toString())
            when {
                response.isSuccessful -> {
                    response.body()
                }
                response.errorBody() != null -> {
                    val converter: Converter<ResponseBody, NewsItem> =
                        RetrofitClientInstance.retrofitInstance!!.responseBodyConverter(

                            Long::class.java, arrayOfNulls<Annotation>(0)
                        )

                    val error = converter.convert(response.errorBody())
                    throw Exception(error?.toString())
                }
                else -> throw Exception(response.code().toString())
            }
        }
    }

    override suspend fun getItemFromId(id: Int): ApiResult<NewsItem> = withContext(context) {
        when (val fromNetwork = ResponseWrapper.safeApiCall { service.getItemFromId(id) }) {
            is ResponseWrapper.GenericError -> ApiResult.ERROR(fromNetwork.error)
            ResponseWrapper.NetworkError -> ApiResult.NetworkError
            is ResponseWrapper.Success -> {
                if (fromNetwork.value.body() != null) {
                    ApiResult.OK(res = fromNetwork.value.body()!!)
                } else {
                    ApiResult.ERROR("Unable to fetch data")
                }
            }
        }
    }

}