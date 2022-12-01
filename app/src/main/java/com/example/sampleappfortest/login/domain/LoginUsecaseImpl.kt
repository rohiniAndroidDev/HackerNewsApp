package  com.example.sampleappfortest.login.domain

import androidx.lifecycle.LiveData
import  com.example.sampleappfortest.common.Result
import  com.example.sampleappfortest.login.data.repository.LoginRepository
import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.model.LoginResponse
import javax.inject.Inject

class LoginUsecaseImpl@Inject constructor(private val mLoginRepository: LoginRepository):LoginUseCase {


    override suspend fun login(loginDetails: LoginDetails): LiveData<Result<LoginResponse>> {
       return mLoginRepository.login(loginDetails)
    }

}