package  com.example.sampleappfortest.login.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose @SerializedName("Status")
    var status: String? = null,
    @Expose @SerializedName("StatusCode")
    var statusCode: Int? = null,
    @Expose
    @SerializedName("Message")
    var message: String? = null)