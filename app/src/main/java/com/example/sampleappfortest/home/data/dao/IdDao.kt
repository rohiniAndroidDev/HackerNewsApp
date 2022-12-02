package com.example.sampleappfortest.home.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.sampleappfortest.home.model.IdModel
import com.example.sampleappfortest.home.model.IdsResponse

@Dao
interface IdDao : BaseDao<IdModel> {
    @Query("SELECT * FROM  idsTable")
    suspend fun getAllIds(): List<IdModel>
}