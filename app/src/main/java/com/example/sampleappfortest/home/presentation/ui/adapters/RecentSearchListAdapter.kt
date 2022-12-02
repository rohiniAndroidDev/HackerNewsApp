package com.example.sampleappfortest.home.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappfortest.R
import com.example.sampleappfortest.home.model.NewSearchList
import kotlinx.android.synthetic.main.search_suggestion_list_item.view.*

class RecentSearchListAdapter :
    RecyclerView.Adapter<RecentSearchListAdapter.RecentSearchListViewHolder>() {
    private var searchList = ArrayList<NewSearchList>()
    private var clickFunction: ((post: NewSearchList, pos: Int) -> Unit)? = null
    fun addClickListener(clickFunction: (NewSearchList, Int) -> Unit) {
        this.clickFunction = clickFunction
    }

    fun updateProductList(searchList: ArrayList<NewSearchList>) {
        this.searchList = searchList
        notifyDataSetChanged()
    }

    inner class RecentSearchListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(newSearchList: NewSearchList, position: Int) {
            with(itemView) {
                suggestionName.text = newSearchList.text
                suggestionMainLayout.setOnClickListener {
                    clickFunction?.invoke(newSearchList,position)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_suggestion_list_item, parent, false)
        return RecentSearchListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentSearchListViewHolder, position: Int) {
        return holder.bind(searchList[position], position)
    }

    override fun getItemCount(): Int {
        return  searchList.size
    }
}
