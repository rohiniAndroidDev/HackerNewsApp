package com.example.sampleappfortest.home.data.network

import android.util.Log
import retrofit2.HttpException
import java.io.IOException

sealed class ResponseWrapper<out T> {
    data class Success<out T>(val value: T) : ResponseWrapper<T>()
    data class GenericError(val code: Int, val error: String) :
        ResponseWrapper<Nothing>()

    object NetworkError : ResponseWrapper<Nothing>()

    companion object {

        private const val safeApiCall = "safeApiCall"

        private const val SOMETHING_WENT_WRONG: String = "Something went wrong"
        private const val SOMETHING_WENT_WRONG_CODE: Int = 500

        @Suppress("SwallowedException", "TooGenericExceptionCaught")
        suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseWrapper<T> {
            var response: ResponseWrapper<T>
            try {
                val res = apiCall.invoke()
                res as retrofit2.Response<*>
                Log.d("sahdhsagdhjag77", res.isSuccessful.toString())
                response = if (res.isSuccessful) {
                    Success(res)
                } else {
                    val errorResponse = getError(res)
                    GenericError(res.code(), errorResponse)
                }
            } catch (httpEx: HttpException) {
                val code = httpEx.code()
                val err = httpEx.response()?.errorBody()?.charStream()
                response = GenericError(code, err.toString())
            } catch (throwable: Throwable) {
                Log.d("sahdhsagdhjag77", throwable.message.toString())
                response = GenericError(
                    SOMETHING_WENT_WRONG_CODE,
                    SOMETHING_WENT_WRONG
                )
            } catch (ioEx: IOException) {
                response = NetworkError
            }
            return response
        }

        private fun getError(res: retrofit2.Response<*>): String {
            return if (res.errorBody() != null) {
                res.errorBody()!!.string()
            } else {
                SOMETHING_WENT_WRONG
            }
        }
    }
}