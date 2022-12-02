package com.example.sampleappfortest.home.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleappfortest.common.INTERNET_ERROR
import com.example.sampleappfortest.common.NETWORK_ERROR
import com.example.sampleappfortest.home.model.*

open class BaseViewModel : ViewModel() {
    private val _viewEvent = MutableLiveData<ViewEvent>()
    val viewEvent: LiveData<ViewEvent> = _viewEvent

    fun postNetworkError() {
        _viewEvent.error(INTERNET_ERROR, NETWORK_ERROR, null)
    }

    fun postLoading() {
        _viewEvent.load()
    }

    fun postIdle() {
        _viewEvent.idle()
    }

    fun postSuccess(data: Any?, message: String, code: Int) {
        _viewEvent.success(data, message, code)
    }

    fun postError(error: String, code: Int, data: Any?) {
        _viewEvent.error(error, code, data)
    }
}

