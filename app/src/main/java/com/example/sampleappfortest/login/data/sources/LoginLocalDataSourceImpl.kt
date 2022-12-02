package  com.example.sampleappfortest.login.data.sources

import  com.example.sampleappfortest.di.qualifiers.IO
import com.example.sampleappfortest.login.data.dao.LoginDao
import  com.example.sampleappfortest.login.data.services.LoginService
import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.model.LoginResponse
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginLocalDataSourceImpl @Inject constructor(
    private val service: LoginService,
    private val loginDao: LoginDao,
    @IO private val context: CoroutineContext
) : LoginLocalDataSource {
    override suspend fun login(loginDetails: LoginDetails): LoginResponse = withContext(context) {
        val isExists = loginDetails.username.let { loginDao.isExists(it) }
        if(isExists == true)
        {
            LoginResponse(status = "Success",200,"OTP Send Successfully")
        }else
        {
            loginDao.insertUser(loginDetails)
            LoginResponse(status = "Success",200,"OTP Send Successfully")

        }
    }

}