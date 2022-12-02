package com.example.sampleappfortest.home.data.converters

import androidx.room.TypeConverter
import com.example.sampleappfortest.home.data.sources.GsonInstance
import com.example.sampleappfortest.home.model.IdModel
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
class ListConverter {

    private val stringListType: Type = object : TypeToken<List<String?>?>() {}.type
    private val intListType: Type = object : TypeToken<List<Int?>?>() {}.type

    @TypeConverter
    fun listToString(list: List<String>?): String {
        return GsonInstance.instance().toJson(list)
    }

    @TypeConverter
    fun listFromString(list: String?): List<String> {
        if (list == null) return emptyList()
        return GsonInstance.instance().fromJson(list, stringListType) as List<String>
    }
    @TypeConverter
    fun listToString(list: ArrayList<String>?): String {
        return GsonInstance.instance().toJson(list)
    }

    @TypeConverter
    fun arrayListFromString(list: String?): ArrayList<String> {
        if (list == null) return arrayListOf()
        return GsonInstance.instance().fromJson(list, stringListType) as ArrayList<String>
    }
    @TypeConverter
    fun  listToType(list: ArrayList<IdModel>?): String {
        return GsonInstance.instance().toJson(list)
    }

    @TypeConverter
    fun arrayListTypeFromString(list: String?): ArrayList<IdModel> {
        if (list == null) return arrayListOf()
        val TypeListType: Type = object : TypeToken<ArrayList<IdModel?>?>() {}.type

        return GsonInstance.instance().fromJson(list, TypeListType) as ArrayList<IdModel>
    }

    @TypeConverter
    fun intListToString(list: List<Int>?): String {
        return GsonInstance.instance().toJson(list)
    }

    @TypeConverter
    fun intListFromString(list: String?): List<Int> {
        if (list == null) return emptyList()
        return GsonInstance.instance().fromJson(list, intListType) as List<Int>
    }
}
