package com.kenshi.presentation.view.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemVideoBinding
import com.kenshi.presentation.item.video.VideoItem
import com.kenshi.presentation.util.extractDateFromDatetime
import com.kenshi.presentation.util.formatPlaytime
import com.kenshi.presentation.util.makeSearchTextBold

class VideoSearchViewHolder(private val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(videoItem: VideoItem) {
        val videoTitle = makeSearchTextBold(videoItem.title)
        val playtime = formatPlaytime(videoItem.playtime)
        val datetime = extractDateFromDatetime(videoItem.datetime)
        itemView.apply {
            binding.apply {
                ivVideoThumbnail.load(videoItem.thumbnail)
                tvVideoTitle.text = videoTitle
                tvVideoPlaytime.text = playtime
                tvVideoDatetime.text = datetime
                tvVideoAuthor.text = videoItem.author
            }
        }
    }
}