package com.example.sampleappfortest.home.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappfortest.R
import com.example.sampleappfortest.databinding.AssignmentListItemBinding
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.util.AppDateUtil
import java.util.*
import kotlin.collections.ArrayList

class SearchNewsAdapter :

    RecyclerView.Adapter<SearchNewsAdapter.SuggestedViewHolder>(), Filterable {

    private var clickFunction: ((post: NewsItem,pos:Int) -> Unit)? = null
    private var likeFunction: ((post: NewsItem,pos:Int) -> Unit)? = null
    private var favFunction: ((post: NewsItem,pos:Int) -> Unit)? = null

    var banners = listOf<NewsItem>()
    var mStringFilterList= ArrayList<NewsItem>()
    var valueFilter: ValueFilter? = null


    fun addClickListener(clickFunction: (NewsItem,Int) -> Unit) {
        this.clickFunction = clickFunction
    }
    fun setLikeItemClickListener(clickFunction: (NewsItem,Int) -> Unit) {
        this.likeFunction = clickFunction
    }
    fun setFavItemClickListener(clickFunction: (NewsItem,Int) -> Unit) {
        this.favFunction = clickFunction
    }


    fun updateBannerList(banners : List<NewsItem>){
        this.banners = banners
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedViewHolder {
        return from(parent)
    }
    fun from(parent: ViewGroup): SearchNewsAdapter.SuggestedViewHolder {
        return SuggestedViewHolder(
            AssignmentListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            ),
        )
    }

    override fun getItemCount(): Int {
        return banners.size
    }

    override fun onBindViewHolder(holderSuggested: SuggestedViewHolder, position: Int) {
        return holderSuggested.bind(banners[position])
    }

    inner class SuggestedViewHolder( private val binding: AssignmentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItem: NewsItem) {

            binding.apply {
                authorNameTextView.text = "By: ${newsItem.by}"
                titleTextView.text = newsItem.title
                commentsCount.text = newsItem.kids?.size.toString()
                timeTextView.text = AppDateUtil.whenDidThisHappen(newsItem.time)
            }
        }
    }

    override fun getFilter(): Filter? {
        if (valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter
    }

    inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            if (constraint != null && constraint.isNotEmpty()) {
                val filterList: MutableList<NewsItem> = ArrayList()
                for (i in 0 until mStringFilterList.size) {
                    if (mStringFilterList[i].title?.uppercase()
                            ?.contains(constraint.toString().uppercase(Locale.getDefault())) == true
                    ) {
                        filterList.add(mStringFilterList[i])
                    }
                }
                results.count = filterList.size
                results.values = filterList
            } else {
                results.count = mStringFilterList.size
                results.values = mStringFilterList
            }
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            banners = results.values as ArrayList<NewsItem>
            notifyDataSetChanged()
        }
    }
}