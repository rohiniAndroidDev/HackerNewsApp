package com.example.sampleappfortest.home.data.sources

import com.example.sampleappfortest.home.data.dao.IdDao
import com.example.sampleappfortest.home.data.dao.NewsDao
import com.example.sampleappfortest.home.model.IdModel
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.util.AppDateUtil
import javax.inject.Inject

class HomeLocalDataSourceImpl  @Inject constructor(private val idsDao: IdDao,private val newsDao: NewsDao) : HomeLocalDataSource{
    override suspend fun getNewsIds(): List<IdModel> {
        return idsDao.getAllIds()
    }

    override suspend fun putIds(list: List<IdModel>) {
        idsDao.insertAll(list)
    }

    override suspend fun getNewsList(): List<NewsItem>? {
        return newsDao.getNewsList()
    }

    override suspend fun storeItemInDb(item: NewsItem) {
        item.setDefaults()
        item.title = item.title?.replace("Show HN: ", "")
            ?.replace("Ask HN: ", "")
        if (item.time != null) {
            item.time = item.time!! * AppDateUtil.DEF_TIME_MULTIPLY
        } else {
            item.time = System.currentTimeMillis()
        }
       return newsDao.insertNewsItem(item)
    }



}