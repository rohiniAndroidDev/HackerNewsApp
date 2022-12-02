package com.example.sampleappfortest.home.presentation.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappfortest.common.FETCHED_IDS
import com.example.sampleappfortest.common.TECHINCIANDETAILS
import com.example.sampleappfortest.common.ViewModelFactory
import com.example.sampleappfortest.common.extensions.isvisible
import com.example.sampleappfortest.common.extensions.showHide
import com.example.sampleappfortest.databinding.FragmentHomeBinding
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.model.ViewEvent
import com.example.sampleappfortest.home.presentation.ui.adapters.LoadingIndicatorAdapter
import com.example.sampleappfortest.home.presentation.ui.adapters.NewsListAdapter
import com.example.sampleappfortest.home.presentation.ui.adapters.UploadedImagesAdapter
import com.example.sampleappfortest.home.presentation.ui.callback.NewsItemClickListener
import com.example.sampleappfortest.home.presentation.ui.viewmodel.HomeViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : DaggerFragment(), NewsItemClickListener {

    var userDetailsString: String? = null
    var mHomeBinding: FragmentHomeBinding? = null
    private lateinit var sharedPrefUtil: SharedPreferences

    //    lateinit var customProgress: CustomProgress
    private var uploadedImagesAdapter: UploadedImagesAdapter? = null
    private lateinit var uploadedNewsAdapter: NewsListAdapter
    private val ASSIGNMENTDATA: String? = "AssignmentData"


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mHomeViewModel: HomeViewModel
    var technicianID = ""
    var technicianName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mHomeBinding = FragmentHomeBinding.inflate(inflater)
        mHomeViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)
        sharedPrefUtil =
            PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        getValueFromPreference()
//        customProgress = CustomProgress(requireContext())


        // Inflate the layout for this fragment
        return mHomeBinding?.root
    }

    private fun getValueFromPreference() {
        userDetailsString = sharedPrefUtil.getString(TECHINCIANDETAILS, "")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        initObserver()
        mHomeViewModel.getNewsIds()

    }


    private fun initAdapter() {
        uploadedImagesAdapter = UploadedImagesAdapter(arrayListOf())
        val mLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        /* mHomeBinding?.assignmentList?.apply {
             adapter=uploadedImagesAdapter
             layoutManager=mLayoutManager
         } */
        uploadedNewsAdapter = NewsListAdapter(this)
//        val mLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        mHomeBinding?.assignmentList?.apply {
            setHasFixedSize(true)
            adapter = uploadedNewsAdapter.withLoadStateHeaderAndFooter(
                header = LoadingIndicatorAdapter { uploadedNewsAdapter.retry() },
                footer = LoadingIndicatorAdapter { uploadedNewsAdapter.retry() }
            )
            layoutManager = mLayoutManager
        }
        mHomeBinding?.errorViewLayout?.retryButton?.setOnClickListener {
            mHomeViewModel.pullData(true)
        }


    }


    private fun initObserver() {
        mHomeViewModel.viewEvent.observe(viewLifecycleOwner) {
            handleViewEvents(it)
        }
        listenToLoadingStates()
    }

    private fun handleViewEvents(viewEvent: ViewEvent) {
        when (viewEvent) {
            is ViewEvent.Error<*> -> {
                mHomeBinding?.errorViewLayout?.errorTextView?.text = viewEvent.error
                showHideLoader(false)
                showHideErrorViews(true)
            }
            ViewEvent.Idle -> {
                showHideLoader(false)
            }
            ViewEvent.Loading -> {
                showHideErrorViews(false)
                showHideLoader(true)
            }
            is ViewEvent.Success<*> -> {
                when (viewEvent.code) {
                    FETCHED_IDS -> {
                        listenToPaginationFlow(viewEvent.data as List<Int>)
                        showHideErrorViews(false)
                    }
                }
            }
        }
    }


    private fun listenToLoadingStates() {
        uploadedNewsAdapter.addLoadStateListener { loadStates ->
            mHomeBinding?.progressBar?.isvisible = loadStates.refresh is LoadState.Loading
            if (loadStates.refresh is LoadState.Error) {
                showHideErrorViews(true)
                mHomeBinding?.errorViewLayout?.errorTextView?.text =
                    (loadStates.refresh as LoadState.Error).error.localizedMessage
            } else {
                showHideErrorViews(false)
            }
        }
    }

    private fun listenToPaginationFlow(ids: List<Int>) {
        lifecycleScope.launch {
            mHomeBinding?.progressBar?.visibility = View.GONE
            mHomeViewModel.getPaginatedFlow(ids).collect {
                uploadedNewsAdapter.submitData(it)
            }
        }
    }

    private fun showHideLoader(show: Boolean) = mHomeBinding?.progressBar?.showHide(show)

    private fun showHideErrorViews(show: Boolean) =
        mHomeBinding?.errorViewLayout?.root?.showHide(show)


    override fun onClick(item: NewsItem) {

    }

    override fun onClickError() {
    }

}
