package  com.example.sampleappfortest.login.presentation.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import  com.example.sampleappfortest.R
import  com.example.sampleappfortest.common.*
import  com.example.sampleappfortest.common.extensions.hideKeyboard
import  com.example.sampleappfortest.databinding.FragmentLoginBinding
import  com.example.sampleappfortest.login.model.LoginDetails
import  com.example.sampleappfortest.login.presentation.viewmodel.LoginViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mLoginViewModel: LoginViewModel
    var mBinding: FragmentLoginBinding? = null
    val activityScope = CoroutineScope(Dispatchers.Main)

    lateinit var customProgress: CustomProgress


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLoginBinding.inflate(inflater)
        customProgress = CustomProgress(requireContext())
        // Inflate the layout for this fragment
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*activityScope.launch {
            delay(1000)
            mBinding?.apply {
                viewVisibleAnimator(logoImageView)
                viewVisibleAnimator(franchiseCodeEditText)
                viewVisibleAnimator(mobileNumberEditText)
                viewVisibleAnimator(nextButton)
            }


        }*/

        lifecycleScope.launchWhenStarted {
            mLoginViewModel =
                ViewModelProviders.of(this@LoginFragment, viewModelFactory)
                    .get(LoginViewModel::class.java)
            initObservers()
            setClickListeners()


        }
    }

    companion object;

    private fun initObservers() {
        mLoginViewModel.loginResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                result->
                when (result.status) {
                    Status.LOADING -> {
                        customProgress.showProgressDialog()

                    }
                    Status.ERROR -> {
                        customProgress.hideProgressDialog()
                        result.data?.message?.let { showToast(it) }

                    }

                    Status.SUCCESS -> {
                        result.data.let { loginResponse ->
                            customProgress.hideProgressDialog()
                            if (loginResponse?.status?.lowercase(Locale.getDefault())
                                    ?.equals("success") == true
                            ) {
                                showToast(getString(R.string.otp_success))
                                view?.let {
                                    Navigation.findNavController(it)
                                        .navigate(R.id.action_loginFragment_to_otpFragment, prepareBundle())
                                }

                            } else {
                                val message = result.data?.message ?: getString(R.string.error_message)
                                showToast(message)


                            }


                        }
                    }

                }
            }


        }
    }



    private fun showSnackBar(data: String?, isError: Boolean) {
        val snackBar = data?.let {
            Snackbar.make(
                mBinding?.loginMainLayout!!, it,
                Snackbar.LENGTH_LONG
            )
        }
        snackBar?.setAction("Close") { snackBar.dismiss() }
        snackBar?.setActionTextColor(resources.getColor(R.color.white))
        val snackBarView = snackBar?.view
        if (isError)
            snackBarView?.setBackgroundColor(resources.getColor(R.color.colorRedDark))
        else
            snackBarView?.setBackgroundColor(resources.getColor(R.color.blue_bg))

        val textView =
            snackBarView?.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(resources.getColor(R.color.white))
        snackBar.show()

    }

    fun onBackPressed() {
        activity?.onBackPressed()
    }

    private fun setClickListeners() {
        mBinding?.nextButton?.setOnClickListener {
            mBinding?.apply {
                hideKeyboard()
                val mobileNumber = mobileNumberEditText.text.toString().trimEnd()
                val franchiseCode = franchiseCodeEditText.text.toString().trimEnd()
                if (mobileNumber.isNotEmpty() && franchiseCode.isNotEmpty()) {
                    if (mobileNumber.length == 10) {
                            mLoginViewModel.login(LoginDetails(franchiseCode, mobileNumber))
                    } else
                        showToast(getString(R.string.phone_no_error_info))

                } else {
                    if (mobileNumber.isEmpty() && franchiseCode.isEmpty()) {
                        showToast(getString(R.string.invaild_values_error))
                    } else if (mobileNumber.isEmpty()) {
                        showToast(getString(R.string.mobile_number_error))
                    } else if (franchiseCode.isEmpty()) {
                        showToast(getString(R.string.email_error))
                    }
                }
            }
        }
    }

   /* private fun showErrorDialog(message: String, isInfo: Boolean=false) {

        val infoDialog=InfoDialog(requireContext(),isInfo=isInfo,message)
        infoDialog.showDialog()
    }*/


    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    private fun viewGoneAnimator(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
    }

    private fun viewVisibleAnimator(view: View) {

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        view.startAnimation(animation)
        //textview will be invisible after the specified amount
        // of time elapses, here it is 1000 milliseconds
        Handler().postDelayed({
            view.visibility = View.VISIBLE
        }, 1000)

    }

    private fun prepareBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(PHONENO, mBinding?.mobileNumberEditText?.text.toString().trimEnd())
        bundle.putString(FRANCHISE_CODE, mBinding?.franchiseCodeEditText?.text.toString().trimEnd())
        return bundle
    }
}