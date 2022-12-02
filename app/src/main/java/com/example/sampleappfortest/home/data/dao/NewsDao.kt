package com.example.sampleappfortest.home.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.login.model.LoginDetails

@Dao
interface NewsDao {
    @Query("select * from newsitem")
    suspend fun getNewsList(): List<NewsItem>
    @Insert
    suspend fun insertNewsList(list:  ArrayList<NewsItem>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsItem(newsItem:  NewsItem)

    @Query("Select * from newsitem where id=:id")
    suspend fun getNewsByID(id: Int): NewsItem?

}