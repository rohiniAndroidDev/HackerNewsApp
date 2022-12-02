package com.example.sampleappfortest.home.data.sources

import com.example.sampleappfortest.common.RetrofitClientInstance
import com.example.sampleappfortest.di.qualifiers.IO
import com.example.sampleappfortest.home.data.service.HomeService
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.ImageDetails
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ProfileDetails
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

    override suspend fun getTopNewsStoriesItemIds(): IdsResponse =withContext(context) {
        val response = service.getTopNewsStoriesItemIds().await()
        when {
            response.isSuccessful -> {
                response.body() ?: throw Exception("no response")
            }
            response.errorBody() != null -> {
                val converter: Converter<ResponseBody, LoginResponse> =
                    RetrofitClientInstance.retrofitInstance!!.responseBodyConverter(

                        Long::class.java, arrayOfNulls<Annotation>(0)
                    )

                val error = converter.convert(response.errorBody())
                throw Exception(error?.message.toString())
            }
            else -> throw Exception(response.code().toString())
        }
    }

    override suspend fun getItemFromId(id: Int): NewsItem = withContext(context) {
        val response = service.getItemFromId(id).await()
        when {
            response.isSuccessful -> {
                response.body() ?: throw Exception("no response")
            }
            response.errorBody() != null -> {
                val converter: Converter<ResponseBody, LoginResponse> =
                    RetrofitClientInstance.retrofitInstance!!.responseBodyConverter(
                        NewsItem::class.java, arrayOfNulls<Annotation>(0)
                    )

                val error = converter.convert(response.errorBody())
                throw Exception(error?.message.toString())
            }
            else -> throw Exception(response.code().toString())
        }
    }

}