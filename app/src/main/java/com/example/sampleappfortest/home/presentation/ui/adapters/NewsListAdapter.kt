package com.example.sampleappfortest.home.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleappfortest.R
import com.example.sampleappfortest.databinding.AssignmentListItemBinding
import com.example.sampleappfortest.home.model.IdsResponse
import com.example.sampleappfortest.home.model.ImageDetails
import com.example.sampleappfortest.home.model.NewsItem
import com.example.sampleappfortest.home.presentation.ui.callback.NewsDiffUtilCallBack
import com.example.sampleappfortest.home.presentation.ui.callback.NewsItemClickListener
import com.example.sampleappfortest.util.AppDateUtil
import java.util.ArrayList

class NewsListAdapter constructor(private val listener: NewsItemClickListener) :
    PagingDataAdapter<NewsItem, NewsListAdapter.StoryViewHolder>(NewsDiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return from(parent)
    }

    fun from(parent: ViewGroup): StoryViewHolder {
        return StoryViewHolder(
            AssignmentListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class StoryViewHolder constructor(
        private val binding: AssignmentListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.mainListCardView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onClick(item)
                        return@setOnClickListener
                    }
                }

                // if didn't return call error
                listener.onClickError()
            }
        }

        fun bind(story: NewsItem) {
            binding.apply {
                authorNameTextView.text = "By: ${story.by}"
                titleTextView.text = story.title
//        commentsImageView.icon =
//          AppIcons.fawComment(binding.root.context)
//        scoreImageView.icon =
//          AppIcons.fawHeart(binding.root.context)
//                scoreTextView.text = story.score.toString()
                commentsCount.text = story.kids?.size.toString()
//                storyTypeTextView.text = story.storyType.string.uppercase()
                timeTextView.text = AppDateUtil.whenDidThisHappen(story.time)
            }
        }
    }
}
/*
class NewsListAdapter(itemList:  ArrayList<NewsItem>) :
    RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    var context: Context? = null
    var itemList:  ArrayList<NewsItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_item, parent, false)
        return MyViewHolder(itemView)
    }
    fun updateProductList(assignmentList: ArrayList<NewsItem>) {
        this.itemList = assignmentList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.img.text=itemList[position].text
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: TextView = view.findViewById<View>(R.id.textView2) as TextView

    }

    init {
        this.itemList = itemList
    }
}*/
