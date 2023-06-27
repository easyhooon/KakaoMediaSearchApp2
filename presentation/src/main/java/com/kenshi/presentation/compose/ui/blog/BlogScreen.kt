package com.kenshi.presentation.compose.ui.blog

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kenshi.presentation.compose.ui.components.BlogCard
import com.kenshi.presentation.item.blog.BlogItem

@Composable
fun BlogScreen(
    blogs: LazyPagingItems<BlogItem>,
    searchQuery: String,
    onClickSeeBlogDetail: (String) -> Unit,
) {
    LazyColumn {
        items(
            count = blogs.itemCount,
            key = blogs.itemKey(key = { blog -> blog.url }),
            contentType = blogs.itemContentType()
        ) { index ->
            val item = blogs[index]
            item?.let {
                BlogCard(blogItem = it, searchQuery, onClick = onClickSeeBlogDetail)
            }
        }
    }
}