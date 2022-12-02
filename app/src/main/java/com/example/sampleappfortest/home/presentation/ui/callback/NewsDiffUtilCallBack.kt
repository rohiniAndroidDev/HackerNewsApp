package com.example.sampleappfortest.home.presentation.ui.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.sampleappfortest.home.model.NewsItem

class NewsDiffUtilCallBack : DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem == newItem
    }
}