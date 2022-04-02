package com.androidera.imagesera

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidera.imagesera.databinding.UnsplashPhotoLoadStateFooterBinding

class UnsplashPhotoLoadStateAdapter(private val retryClickListener: () -> Unit) :
    LoadStateAdapter<UnsplashPhotoLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            UnsplashPhotoLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retryClickListener)
    }


    class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retryClickListener: () -> Unit) {
            with(binding) {
                stateFooterProgressBar.isVisible = loadState is LoadState.Loading
                stateFooterRetryButton.isVisible = loadState !is LoadState.Loading
                stateFooterErrorTextView.isVisible = loadState !is LoadState.Loading

                stateFooterRetryButton.setOnClickListener { retryClickListener() }
            }
        }
    }


}