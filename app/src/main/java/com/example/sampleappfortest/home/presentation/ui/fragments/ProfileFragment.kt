package com.example.sampleappfortest.home.presentation.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.sampleappfortest.R
import com.example.sampleappfortest.common.*
import com.example.sampleappfortest.databinding.FragmentProfileBinding
import com.example.sampleappfortest.home.model.*
import com.example.sampleappfortest.home.presentation.ui.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*
import javax.inject.Inject


class ProfileFragment : DaggerFragment() {

    var mProfileBinding: FragmentProfileBinding? = null
    private lateinit var sharedPrefUtil: SharedPreferences
    lateinit var customProgress: CustomProgress
    private val ASSIGNMENTDATA: String? = "AssignmentData"


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mHomeViewModel: HomeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mProfileBinding = FragmentProfileBinding.inflate(inflater)
        mHomeViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)
        sharedPrefUtil =
            PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        customProgress = CustomProgress(requireContext())


        // Inflate the layout for this fragment
        return mProfileBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        mHomeViewModel.getProfile()

    }
    private fun init() {
        mProfileBinding?.apply{


            userName.apply {
                profileHintTextview.text=getString(R.string.name_hint)
            }
            userId.apply {
                profileHintTextview.text=getString(R.string.global_id_hint)
            }
            userEmail.apply {
                profileHintTextview.text=getString(R.string.email)
            }
            userPhone.apply {
                profileHintTextview.text=getString(R.string.business_number_text)
            }
        }

    }


    private fun initObserver() {
        mHomeViewModel.profileResult.observe(viewLifecycleOwner)
        {
            it.getContentIfNotHandled()?.let {
                profileObserver(it)
            }
            it.peekContent().let {
                profileObserver(it)
            }
        }

    }

    private fun categoryObserver(result: Result<ImageDetails>) {
        when (result.status) {
            Status.LOADING -> {

                customProgress.showProgressDialog()

            }
            Status.ERROR -> {
                customProgress.hideProgressDialog()

            }

            Status.SUCCESS -> {
                customProgress.hideProgressDialog()
                handleResponse(result.data)


            }

        }
    }

    private fun handleResponse(response: ImageDetails?) {


    }


    private fun profileObserver(result: Result<ProfileDetails>) {
        when (result.status) {
            Status.LOADING -> {


            }
            Status.ERROR -> {
//
            }

            Status.SUCCESS -> {
                result.data?.let {
                    handleProfileValues(it)
                }

            }

        }
    }

    private fun handleProfileValues(profileDetails: ProfileDetails) {
        profileDetails.apply {
            mProfileBinding?.apply {
                userName.profileText.text=name
                userEmail.profileText.text=email
    //                user_phone.profileText.text=name
            }
        }



    }


    private fun showToast(message: String) {

        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }


}
