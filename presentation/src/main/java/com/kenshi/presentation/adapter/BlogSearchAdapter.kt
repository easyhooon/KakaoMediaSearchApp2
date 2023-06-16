package com.kenshi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kenshi.presentation.adapter.viewholder.BlogSearchViewHolder
import com.kenshi.presentation.databinding.ItemBlogBinding
import com.kenshi.presentation.item.blog.BlogItem

class BlogSearchAdapter : PagingDataAdapter<BlogItem, BlogSearchViewHolder>(
    BlogItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogSearchViewHolder {
        return BlogSearchViewHolder(
            ItemBlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BlogSearchViewHolder, position: Int) {
        val blogItem = getItem(position)
        blogItem?.let { blog ->
            holder.bind(blog)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(blog)}
            }
        }
    }

    private var onItemClickListener: ((BlogItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (BlogItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val BlogItemDiffCallback = object : DiffUtil.ItemCallback<BlogItem>() {
            override fun areItemsTheSame(
                oldItem: BlogItem,
                newItem: BlogItem,
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: BlogItem,
                newItem: BlogItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}