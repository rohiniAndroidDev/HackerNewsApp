package com.example.sampleappfortest.home.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleappfortest.R
import com.example.sampleappfortest.home.model.ImageDetails


class UploadedImagesAdapter(itemList: List<ImageDetails>) :
    RecyclerView.Adapter<UploadedImagesAdapter.MyViewHolder>() {
    var context: Context? = null
    var itemList: List<ImageDetails>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item, parent, false)
        return MyViewHolder(itemView)
    }
    fun updateProductList(assignmentList: List<ImageDetails>) {
        this.itemList = assignmentList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.img.context)
            .load(itemList[position].avatar)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView

        init {
            img = view.findViewById<View>(R.id.img_icon) as ImageView
        }
    }

    init {
        this.itemList = itemList
    }
}