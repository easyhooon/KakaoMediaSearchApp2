package com.kenshi.presentation.compose.ui.screens.blog

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kenshi.presentation.compose.ui.components.BlogCard
import com.kenshi.presentation.item.blog.BlogItem
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BlogScreen(
    searchQuery: String,
    blogs: LazyPagingItems<BlogItem>,
    onClickSeeBlogDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = searchQuery) {
        snapshotFlow { blogs.loadState.refresh }
            .filter { it is LoadState.Loading }
            .collect {
                listState.animateScrollToItem(0)
                controller?.hide()
            }
    }

    LazyColumn(state = listState) {
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
//        loadStateFooterWithRetry(
//            pagingItems = blogs,
//            onRetry = { blogs.retry() }
//        )
    }
}