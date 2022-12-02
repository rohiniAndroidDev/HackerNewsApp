package com.example.sampleappfortest.home.data.sources

import com.example.sampleappfortest.home.model.IdModel
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.NewsItem


interface HomeLocalDataSource {
    suspend fun getNewsIds(): List<IdModel>

    suspend fun putIds(list: List<IdModel>)

    suspend fun getNewsList(): List<NewsItem>
    suspend fun storeItemInDb(item: NewsItem)
    suspend fun getNewsById(id:Int):NewsItem?

}