package com.kenshi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kenshi.presentation.adapter.viewholder.ImageSearchViewHolder
import com.kenshi.presentation.databinding.ItemImageBinding
import com.kenshi.presentation.item.image.ImageItem

class ImageSearchAdapter : PagingDataAdapter<ImageItem, ImageSearchViewHolder>(
    ImageItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSearchViewHolder {
        return ImageSearchViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageSearchViewHolder, position: Int) {
        val imageItem = getItem(position)
        imageItem?.let { image ->
            holder.bind(image)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(image)}
            }
        }
    }

    private var onItemClickListener: ((ImageItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (ImageItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val ImageItemDiffCallback = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(
                oldItem: ImageItem,
                newItem: ImageItem,
            ): Boolean {
                return oldItem.thumbnailUrl == newItem.thumbnailUrl
            }

            override fun areContentsTheSame(
                oldItem: ImageItem,
                newItem: ImageItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}