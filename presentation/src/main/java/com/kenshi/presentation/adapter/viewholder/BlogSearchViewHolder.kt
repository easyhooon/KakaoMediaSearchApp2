package com.kenshi.presentation.adapter.viewholder

import android.text.Html
import android.text.SpannableString
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kenshi.presentation.databinding.ItemBlogBinding
import com.kenshi.presentation.item.blog.BlogItem

class BlogSearchViewHolder(
    private val binding: ItemBlogBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(blogItem: BlogItem) {
        val blogTitle = makeSearchTextBold(blogItem.title)
        val datetime =
            if (blogItem.datetime.isNotEmpty()) blogItem.datetime.substring(0, 10) else ""

        itemView.apply {
            binding.apply {
                ivBlogThumbnail.load(blogItem.thumbnail)
                tvBlogTitle.text = blogTitle
                tvBlogDatetime.text = datetime
                tvBlogName.text = blogItem.blogName
            }
        }
    }

    private fun makeSearchTextBold(text: String): SpannableString {
        return SpannableString(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT))
    }
}