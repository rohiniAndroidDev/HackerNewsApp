package com.example.sampleappfortest.home.presentation.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappfortest.R
import com.example.sampleappfortest.common.CustomProgress
import com.example.sampleappfortest.common.Result
import com.example.sampleappfortest.common.Status
import com.example.sampleappfortest.common.ViewModelFactory
import com.example.sampleappfortest.common.extensions.gone
import com.example.sampleappfortest.common.extensions.visible
import com.example.sampleappfortest.databinding.FragmentHomeBinding
import com.example.sampleappfortest.databinding.FragmentSearchBinding
import com.example.sampleappfortest.home.model.NewSearchList
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.presentation.ui.adapters.RecentSearchListAdapter
import com.example.sampleappfortest.home.presentation.ui.adapters.SearchNewsAdapter
import com.example.sampleappfortest.home.presentation.ui.viewmodel.HomeViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    var mSearchBinding: FragmentSearchBinding? = null

    private var sharedPrefSearchMap = HashMap<String,Long>()
    private var sharedPrefSearchList = ArrayList<NewSearchList>()
    private lateinit var sharedPrefUtil: SharedPreferences
    val SEARCHITEMSLIST="SearchItemsList"
    private var searchListAdapter : RecentSearchListAdapter? = null
    val bannerAdapter = SearchNewsAdapter()
    private var listOfNews = listOf<NewsItem>()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mHomeViewModel: HomeViewModel

    lateinit var customProgress: CustomProgress
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSearchBinding = FragmentSearchBinding.inflate(inflater)
        mHomeViewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)
        customProgress = CustomProgress(requireContext())
        // Inflate the layout for this fragment
        return mSearchBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initObserver()
        initNewsAdapterData()
        mHomeViewModel.getNewsListFromLocalDb()
    }

    private fun initObserver() {
        mHomeViewModel.newsResult.observe(viewLifecycleOwner)
        {
            it.getContentIfNotHandled()?.let {
                newsListObserver(it)
            }
            it.peekContent().let {
                newsListObserver(it)
            }
        }
    }
    private fun newsListObserver(result: Result<List<NewsItem>>) {
        when (result.status) {
            Status.LOADING -> {

                customProgress.showProgressDialog()

            }
            Status.ERROR -> {
                customProgress.hideProgressDialog()

            }

            Status.SUCCESS -> {
                customProgress.hideProgressDialog()
//                result.data?.let { mHomeViewModel.getNewsList(it) }
                handleNewsIdsResponse(result.data)


            }

        }
    }
    private fun handleNewsIdsResponse(data: List<NewsItem>?) {
        if (data?.isNullOrEmpty() == true) {
            mSearchBinding?.llNoData.visible()
            mSearchBinding?.newsListView.gone()

        } else {
            updateNewsAdapterData(data)
            mSearchBinding?.llNoData.gone()
            mSearchBinding?.newsListView.visible()

        }
    }

    private fun updateNewsAdapterData(data: List<NewsItem>) {
        listOfNews=data
        data?.let { bannerAdapter.updateBannerList(it) }
    }

    private fun initNewsAdapterData() {
            mSearchBinding?.newsListView?.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter=bannerAdapter
            }

    }


    private fun setListeners() {
        mSearchBinding?.apply {
            backButton.setOnClickListener {
                if(searchGroup.visibility==View.VISIBLE)
                {
                    searchGroup.visibility=View.GONE
                    titleGroup.visibility=View.VISIBLE
                    etSearch.setText("")
                    newsListView.visibility = View.VISIBLE
                    llNoData.visibility = View.GONE
                    bannerAdapter.updateBannerList(listOfNews)
                }else {
                   requireActivity().onBackPressed()
                }
            }
            searchButton.setOnClickListener {

                searchGroup.visibility=View.VISIBLE
                titleGroup.visibility=View.GONE
                etSearch.requestFocus()
            }
            closeButton.setOnClickListener {
                etSearch.setText("")
                performSearch()
                etSearch.clearFocus()
            }
            etSearch.addTextChangedListener {
                if(!it?.toString().isNullOrBlank())
                {
                    it?.toString()?.let { it1 -> filter(it1) }
                }
            }
        }

    }

    private fun performSearch() {
        mSearchBinding?.apply{
            etSearch.clearFocus()
            val `in`: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            `in`.hideSoftInputFromWindow(etSearch.windowToken, 0)
            filter(etSearch.text.toString())
        }

    }
    fun filter(text: String) {
        val tempOrders: ArrayList<NewsItem> = ArrayList()
        for (order in listOfNews) {
            if (order.title?.toLowerCase()?.contains(text.toLowerCase()) == true)
                tempOrders.add(order)
        }

        mSearchBinding?.apply {
            if (tempOrders.isNullOrEmpty()){
                llNoData.visibility = View.VISIBLE
                newsListView.visibility = View.GONE
            }else{
                newsListView.visibility = View.VISIBLE
                llNoData.visibility = View.GONE
                bannerAdapter.updateBannerList(tempOrders)
            }
        }



    }
}