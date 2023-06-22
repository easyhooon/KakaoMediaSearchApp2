package com.kenshi.presentation.compose.ui.blog

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kenshi.presentation.compose.ui.components.BlogCard
import com.kenshi.presentation.item.blog.BlogItem

@Composable
fun BlogScreen(
    blogs: LazyPagingItems<BlogItem>,
    searchQuery: String,
    onClickSeeBlogDetail: (String) -> Unit = {},
) {
    LazyColumn {
        items(
            items = blogs,
            key = { blog ->
                blog.url
            }
        ) { blog ->
            blog?.let {
                BlogCard(blogItem = it, searchQuery, onClick = onClickSeeBlogDetail)
            }
        }
    }
}