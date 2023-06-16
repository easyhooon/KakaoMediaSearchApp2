package com.kenshi.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemImageBinding
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.util.extractDateFromDatetime

class ImageSearchViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageItem: ImageItem) {
        val datetime = extractDateFromDatetime(imageItem.datetime)
        itemView.apply {
            binding.apply {
                ivImageThumbnail.load(imageItem.thumbnailUrl)
                tvImageTitle.text = imageItem.displaySiteName
                tvImageDatetime.text = datetime
                tvImageSiteName.text = imageItem.collection
            }
        }
    }
}