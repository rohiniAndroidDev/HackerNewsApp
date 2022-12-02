package com.example.sampleappfortest.home.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappfortest.common.extensions.isVisible
import com.example.sampleappfortest.common.extensions.setViewVisibility
import com.example.sampleappfortest.databinding.LoadingIndicatorLayoutBinding

class LoadingIndicatorAdapter constructor(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingIndicatorViewHolder>() {
    override fun onBindViewHolder(holder: LoadingIndicatorViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingIndicatorViewHolder {
        return LoadingIndicatorViewHolder.from(parent, retry)
    }
}
class LoadingIndicatorViewHolder private constructor(
    private val binding: LoadingIndicatorLayoutBinding,
    retry: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            progressbar.setViewVisibility (loadState is LoadState.Loading)
            statusTextView.setViewVisibility(loadState !is LoadState.Loading)
            retryButton.setViewVisibility(loadState !is LoadState.Loading)
        }
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): LoadingIndicatorViewHolder {
            return LoadingIndicatorViewHolder(
                LoadingIndicatorLayoutBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent,
                    false
                ),
                retry
            )
        }
    }
}
