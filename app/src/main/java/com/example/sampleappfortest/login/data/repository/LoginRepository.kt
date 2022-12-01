package  com.example.sampleappfortest.login.data.repository

import androidx.lifecycle.LiveData
import  com.example.sampleappfortest.common.Result
import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.model.LoginResponse

interface LoginRepository {
    suspend fun login(loginDetails: LoginDetails): LiveData<Result<LoginResponse>>

}