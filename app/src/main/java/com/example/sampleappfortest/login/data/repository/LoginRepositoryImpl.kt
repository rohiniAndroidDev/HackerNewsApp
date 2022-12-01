package  com.example.sampleappfortest.login.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import  com.example.sampleappfortest.login.data.sources.LoginLocalDataSource
import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.model.LoginResponse
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginLocalDataSource
) : LoginRepository {
    override suspend fun login(loginDetails: LoginDetails): LiveData<com.example.sampleappfortest.common.Result<LoginResponse>> {
        Log.d("dajhdjh", "Visa389880")
        return liveData {
            emit(com.example.sampleappfortest.common.Result.loading())
            try {
                val response = remoteDataSource.login(loginDetails)
                emit(com.example.sampleappfortest.common.Result.success(response))

            } catch (exception: Exception) {
                emit(
                    com.example.sampleappfortest.common.Result.error(
                        exception.message ?: "",
                        null
                    )
                )
            }
        }
    }
}