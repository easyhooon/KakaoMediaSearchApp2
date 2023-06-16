package com.kenshi.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemVideoBinding
import com.kenshi.presentation.item.video.VideoItem

class VideoSearchViewHolder(
    private val binding: ItemVideoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(videoItem: VideoItem) {
        itemView.apply {
            binding.apply {
                ivVideoThumbnail.load(videoItem.url)
                tvVideoTitle.text = videoItem.title
                tvVideoPlaytime.text = videoItem.playtime.toString()
                tvVideoDatetime.text = videoItem.datetime
                tvVideoAuthor.text = videoItem.author
            }
        }
    }
}