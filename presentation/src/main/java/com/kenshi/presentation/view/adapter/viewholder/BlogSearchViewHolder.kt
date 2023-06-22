package com.kenshi.presentation.view.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemBlogBinding
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.util.extractDateFromDatetime
import com.kenshi.presentation.util.makeSearchTextBold

class BlogSearchViewHolder(private val binding: ItemBlogBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(blogItem: BlogItem) {
        val blogTitle = makeSearchTextBold(blogItem.title)
        val datetime = extractDateFromDatetime(blogItem.datetime)
        itemView.apply {
            binding.apply {
                ivBlogThumbnail.load(blogItem.thumbnail)
                tvBlogTitle.text = blogTitle
                tvBlogDatetime.text = datetime
                tvBlogName.text = blogItem.blogName
            }
        }
    }
}