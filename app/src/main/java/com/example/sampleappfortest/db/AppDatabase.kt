package com.example.sampleappfortest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.sampleappfortest.home.data.converters.ListConverter
import com.example.sampleappfortest.home.data.dao.IdDao
import com.example.sampleappfortest.home.data.dao.NewsDao
import com.example.sampleappfortest.home.model.IdModel
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.login.data.dao.LoginDao
import com.example.sampleappfortest.login.model.LoginDetails


@Database(entities = [LoginDetails::class,NewsItem::class,IdModel::class], version = 5)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao
    abstract fun newsDao(): NewsDao
    abstract fun idsDao(): IdDao
}