package com.example.sampleappfortest.home.presentation.ui.viewmodel

import androidx.lifecycle.*
import com.example.sampleappfortest.common.*
import com.example.sampleappfortest.home.domain.HomeUseCase
import com.example.sampleappfortest.home.model.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val mHomeUserCase: HomeUseCase) : BaseViewModel() {

    init {
        pullData(false)
    }
    val profileResult =
        MediatorLiveData<Event<com.example.sampleappfortest.common.Result<ProfileDetails>>>()

    fun getProfile() {
        viewModelScope.launch {
            profileResult.addSource(mHomeUserCase.getProfile()) {
                profileResult.value = Event(it)
            }
        }
    }
    val newsIDsResult =
        MediatorLiveData<Event<com.example.sampleappfortest.common.Result<IdsResponse>>>()

    /*fun getNewsIds() {
        viewModelScope.launch {
            newsIDsResult.addSource(mHomeUserCase.getTopNewsStoriesItemIds()) {
                newsIDsResult.value = Event(it)
            }
        }
    }*/
    val newsResult = MediatorLiveData<Event<com.example.sampleappfortest.common.Result<ArrayList<NewsItem>>>>()


   /* fun getNewsList(id:IdsResponse) {
        viewModelScope.launch {
            newsResult.addSource(mHomeUserCase.getItemFromId(id)) {
                newsResult.value = Event(it)
            }
        }
    }*/

    val getCategoryResult =
        MediatorLiveData<Event<com.example.sampleappfortest.common.Result<List<ImageDetails>>>>()

    fun getImages() {
        viewModelScope.launch {
            getCategoryResult.addSource(mHomeUserCase.getImages())
            {
                getCategoryResult.value = Event(it)

            }
        }
    }
    fun getPaginatedFlow(idsList: List<Int>) = mHomeUserCase.invoke(idsList)

    fun pullData(isRetry:Boolean) {
        viewModelScope.launch {
            postLoading()
            val response = mHomeUserCase.invoke(isRetry)
            if (response.success)
                postSuccess(response.result!!, response.message,FETCHED_IDS)
            else
                postError(response.message, API_ERROR, null)
        }
    }


}