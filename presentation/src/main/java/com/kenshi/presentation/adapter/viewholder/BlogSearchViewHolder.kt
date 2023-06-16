package com.kenshi.presentation.adapter.viewholder

import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
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
        val spannedText = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        val spannableString = SpannableString(spannedText)

        val pattern = "<b>(.*?)</b>".toRegex()
        val matches = pattern.findAll(text)

        for (match in matches) {
            val startIndex = match.range.first
            val endIndex = match.range.last

            if (startIndex < spannableString.length && endIndex < spannableString.length) {
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex,
                    endIndex - 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableString
    }
}