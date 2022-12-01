package  com.example.sampleappfortest.login.data.sources

import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.model.LoginResponse

interface LoginLocalDataSource {
    suspend fun login(loginDetails: LoginDetails): LoginResponse


}