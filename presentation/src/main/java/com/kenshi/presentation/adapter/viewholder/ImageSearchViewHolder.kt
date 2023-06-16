package com.kenshi.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemImageBinding
import com.kenshi.presentation.item.image.ImageItem

class ImageSearchViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageItem: ImageItem) {
        itemView.apply {
            binding.apply {
                ivImageThumbnail.load(imageItem.thumbnailUrl)
                tvImageDatetime.text = imageItem.datetime
                tvImageSiteName.text = imageItem.displaySiteName
            }
        }
    }
}