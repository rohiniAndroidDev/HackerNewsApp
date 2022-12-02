package com.example.sampleappfortest.home.presentation.ui.callback

import com.example.sampleappfortest.home.model.NewsItem


interface NewsItemClickListener {
    fun onClick(item: NewsItem)

    fun onClickError()
}