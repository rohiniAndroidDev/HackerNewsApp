package com.example.sampleappfortest.login.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sampleappfortest.login.model.LoginDetails

@Dao
interface LoginDao {
    @Query("select * from logindetails where username=:username")
    suspend fun getUserByname(username:String): LoginDetails
    @Insert
    suspend fun insertUser(loginDetails: LoginDetails)

    @Query("SELECT EXISTS (SELECT 1 FROM logindetails WHERE username = :fileId)")
    fun isExists(fileId: String): Boolean
}