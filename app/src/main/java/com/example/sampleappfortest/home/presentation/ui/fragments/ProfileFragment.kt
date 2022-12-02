package com.example.sampleappfortest.home.presentation.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.sampleappfortest.R
import com.example.sampleappfortest.common.*
import com.example.sampleappfortest.databinding.FragmentProfileBinding
import com.example.sampleappfortest.home.model.*
import com.example.sampleappfortest.home.presentation.ui.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment

import javax.inject.Inject


class ProfileFragment : DaggerFragment() {

    var mProfileBinding: FragmentProfileBinding? = null
    private lateinit var sharedPrefUtil: SharedPreferences
    lateinit var customProgress: CustomProgress


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
        init()
        mHomeViewModel.getProfile()

    }
    private fun init() {
        mProfileBinding?.apply{


            userName.apply {
                profileHintTextview.text=getString(R.string.username_cc)

            }
            userId.apply {
                profileHintTextview.text=getString(R.string.user_id_cc)

            }
            userEmail.apply {
                profileHintTextview.text=getString(R.string.about_cc)

            }
            userPhone.apply {
                profileHintTextview.text=getString(R.string.published_news_cc)

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

    private fun profileObserver(result: Result<ProfileDetails>) {
        when (result.status) {
            Status.LOADING -> {

                customProgress.showProgressDialog()

            }
            Status.ERROR -> {
                customProgress.hideProgressDialog()
            }

            Status.SUCCESS -> {
                customProgress.hideProgressDialog()
                result.data?.let {
                    handleProfileValues(it)
                }

            }

        }
    }

    private fun handleProfileValues(profileDetails: ProfileDetails) {
        profileDetails.apply {
            mProfileBinding?.apply {
                ivAvtar.text=id.toUpperCase()
                userId.apply {
                    profileText.text=id
                }
                    userName.apply {
                    profileText.text="Demo User"
                }
                userEmail.apply {
                    profileText.text=about
                }
                userPhone.apply {
                    profileText.text="Submitted ${submitted.size} News"
                }
            }
        }



    }




}
