package com.androidera.imagesera

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidera.imagesera.databinding.RecyclerViewUnsplashPhotoListItemBinding
import com.androidera.imagesera.model.UnsplashPhoto
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UnsplashPhotoAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.UnsplashPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashPhotoViewHolder {
        return UnsplashPhotoViewHolder(
            RecyclerViewUnsplashPhotoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UnsplashPhotoViewHolder, position: Int) {
        val photo = getItem(position)
        photo?.let { photoItem -> holder.bind(photoItem) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem == newItem
        }

    }

    class UnsplashPhotoViewHolder(private val binding: RecyclerViewUnsplashPhotoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(unsplashPhotoImageView)

                unsplashUserNameTextView.text = photo.user.username
            }
        }
    }
}