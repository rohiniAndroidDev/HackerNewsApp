package  com.example.sampleappfortest.login.presentation.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import  com.example.sampleappfortest.home.presentation.ui.activities.HomeActivity
import  com.example.sampleappfortest.R
import  com.example.sampleappfortest.common.*
import  com.example.sampleappfortest.databinding.FragmentOtpBinding
import  com.example.sampleappfortest.login.model.*
import  com.example.sampleappfortest.login.presentation.viewmodel.LoginViewModel
import com.example.sampleappfortest.common.extensions.hideKeyboard
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class OtpFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mLoginViewModel: LoginViewModel
    var mBindingFragmentOtpBinding: FragmentOtpBinding? = null
    lateinit var customProgress: CustomProgress
    private var isValid = false
    private var phoneNumber = ""
    private var franchiseCode = ""
    private lateinit var sharedPrefUtil: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindingFragmentOtpBinding = FragmentOtpBinding.inflate(inflater)
        customProgress = CustomProgress(requireContext())
        sharedPrefUtil = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)

        // Inflate the layout for this fragment
        return mBindingFragmentOtpBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)
        getIntentValues()
        initObservers()
        setListeners()

    }

    private fun getIntentValues() {

        phoneNumber = arguments?.getString(PHONENO).toString()
        franchiseCode = arguments?.getString(FRANCHISE_CODE).toString()
    }

    private fun setListeners() {

        mBindingFragmentOtpBinding?.apply {
            otpVerification.addTextChangedListener {
                isValid = it.toString().length == 6

            }

            submitButton.setOnClickListener {
//                resetError()
                if (isValid) {
                    hideKeyboard()
                    val otp = otpVerification.text.toString()
                    if(otp == "123456")
                    {
                        mLoginViewModel.setSuccess()
                    }else
                    {
                        mLoginViewModel.setFailure()
                    }
                } else {
                    showToast("Please fill 6 digits")
                    return@setOnClickListener
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()

    }

    /* private fun enableError(message: String, isInfo: Boolean = false) {
         val infoDialog = InfoDialog(requireContext(), isInfo = isInfo, message)
         infoDialog.showDialog()
     }*/

    /* private fun resetError() {
         mBindingFragmentOtpBinding?.apply {
             otpVerification.setLineColor(
                 ContextCompat.getColor(
                     requireContext(),
                     R.color.blue_bg
                 )
             )
             otpVerification.setTextColor(resources.getColor(R.color.white))

             otpErrorMessageText.visibility = View.GONE
             otpVerification.invalidate()
         }
     }*/
    private fun initObservers() {
        mLoginViewModel.otpResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                loginObserver(it)
            }
            loginObserver(it.peekContent())

        }
        mLoginViewModel.resentOTPResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                resentOTPObserver(it)
            }
            it.peekContent().let {
                resentOTPObserver(it)
            }

        }
    }

    private fun resentOTPObserver(result: Result<LoginResponse>) {
        when (result.status) {
            Status.LOADING -> {
                customProgress.showProgressDialog()

            }
            Status.ERROR -> {
                customProgress.hideProgressDialog()
                showToast(result.message.toString())
            }

            Status.SUCCESS -> {
                result.data.let { loginResponse ->
                    customProgress.hideProgressDialog()
                    if (loginResponse?.status?.lowercase(Locale.getDefault())
                            ?.equals("success") == true
                    ) {
                        showToast(getString(R.string.otp_success))
                    } else {
                        val message = result.data?.message ?: getString(R.string.error_message)
                        showToast(message)
                    }
                }
            }
        }
    }

    private fun loginObserver(result: Result<LoginResponse>) {
        when (result.status) {
            Status.LOADING -> {
                customProgress.showProgressDialog()

            }
            Status.ERROR -> {
                customProgress.hideProgressDialog()
                showToast(result.message.toString())
            }

            Status.SUCCESS -> {
                result.data.let { loginResponse ->
                    customProgress.hideProgressDialog()
                    if (loginResponse?.status?.lowercase(Locale.getDefault())
                            ?.equals("success") == true
                    ) {
                        saveLoginDetails(loginResponse)
                        Log.d("djdhajhjd", "Dajfjfhj")
                        openMainActivity()
                    } else {
                        val message = result.data?.message ?: getString(R.string.error_message)
                        showToast(message)

                    }


                }
            }

        }
    }

    private fun saveLoginDetails(loginResponse: LoginResponse?) {
//        val technicianName=if(loginResponse?.data is Data) loginResponse.data.employeeName else ""

        val editor = sharedPrefUtil.edit()
        editor.putBoolean(ISLOGGEDIN, true)
       /* if (technicianData != null)
            editor.putString(TECHINCIANDETAILS, technicianData)*/
        editor.apply()
    }

    private fun openMainActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}