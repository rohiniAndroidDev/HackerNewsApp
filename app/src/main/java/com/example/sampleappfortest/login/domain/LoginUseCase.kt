package  com.example.sampleappfortest.login.domain

import androidx.lifecycle.LiveData
import  com.example.sampleappfortest.common.Result
import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.model.LoginResponse

interface LoginUseCase {
   suspend fun login(loginDetails: LoginDetails): LiveData<Result<LoginResponse>>
}