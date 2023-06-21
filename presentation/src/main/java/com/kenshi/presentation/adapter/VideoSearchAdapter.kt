package com.kenshi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kenshi.presentation.adapter.viewholder.VideoSearchViewHolder
import com.kenshi.presentation.databinding.ItemVideoBinding
import com.kenshi.presentation.item.video.VideoItem

class VideoSearchAdapter :
    PagingDataAdapter<VideoItem, VideoSearchViewHolder>(VideoItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoSearchViewHolder {
        return VideoSearchViewHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoSearchViewHolder, position: Int) {
        val videoItem = getItem(position)
        videoItem?.let { video ->
            holder.bind(video)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(video.url) }
            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val VideoItemDiffCallback = object : DiffUtil.ItemCallback<VideoItem>() {
            override fun areItemsTheSame(
                oldItem: VideoItem,
                newItem: VideoItem,
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: VideoItem,
                newItem: VideoItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}