package  com.example.sampleappfortest.login.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappfortest.common.Event
import com.example.sampleappfortest.common.Result
import com.example.sampleappfortest.common.Status
import com.example.sampleappfortest.login.domain.LoginUseCase
import com.example.sampleappfortest.login.model.LoginDetails
import com.example.sampleappfortest.login.model.LoginResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val mLoginUseCase: LoginUseCase) : ViewModel() {

    val loginResult = MediatorLiveData<Event<Result<LoginResponse>>>()
    fun login(loginDetails: LoginDetails) {
        viewModelScope.launch {
            loginResult.addSource(mLoginUseCase.login(loginDetails)) {
                loginResult.value = Event(it)
            }
        }
    }

    val resentOTPResult = MediatorLiveData<Event<Result<LoginResponse>>>()
    fun resentOTP(loginDetails: LoginDetails) {
        viewModelScope.launch {
            resentOTPResult.addSource(mLoginUseCase.login(loginDetails)) {
                resentOTPResult.value = Event(it)
            }
        }
    }

    fun setSuccess() {
        val result = com.example.sampleappfortest.common.Result<LoginResponse>(
            Status.SUCCESS,
            LoginResponse("Success", 200, "Otp verified"),
            "Success"
        )
        otpResult.value = Event(result)
    }
    fun setFailure() {
        val result = com.example.sampleappfortest.common.Result<LoginResponse>(
            Status.ERROR,
            LoginResponse("Failure", 500, "Something went wrong,Please try again!"),
            "Success"
        )
        otpResult.value = Event(result)
    }

    val otpResult = MutableLiveData<Event<Result<LoginResponse>>>()

}