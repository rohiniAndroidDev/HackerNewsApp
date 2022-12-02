package com.example.sampleappfortest.home.model

import androidx.lifecycle.MutableLiveData
import com.example.sampleappfortest.common.INTERNET_ERROR
import com.example.sampleappfortest.common.INTERNET_ERROR_CODE

sealed class ViewEvent {
    object Idle : ViewEvent()
    data class Success<T>(val data: T? = null, val code: Int, val message: String) : ViewEvent() {
        override fun toString(): String {
            return "Success(data=$data, code=$code, message='$message')"
        }
    }

    object Loading : ViewEvent()
    data class Error<T>(val error: String, val code: Int, val data: T?) : ViewEvent() {
        override fun toString(): String {
            return "Error(error=$error, code=$code)"
        }
    }
}

fun MutableLiveData<ViewEvent>.load() {
    this.postValue(ViewEvent.Loading)
}

fun MutableLiveData<ViewEvent>.idle() {
    this.postValue(ViewEvent.Idle)
}

fun MutableLiveData<ViewEvent>.success(data: Any?, message: String, code: Int) {
    this.postValue(ViewEvent.Success(data = data, code, message))
}

fun MutableLiveData<ViewEvent>.error(error: String, code: Int, data: Any?) {
    this.postValue(ViewEvent.Error(error, code, data))
}

fun MutableLiveData<ViewEvent>.networkError() {
    this.postValue(
        ViewEvent.Error(
            error = INTERNET_ERROR,
            INTERNET_ERROR_CODE, null
        )
    )
}

