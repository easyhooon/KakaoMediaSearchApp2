package com.kenshi.presentation.compose.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kenshi.presentation.R
import com.kenshi.presentation.compose.ui.components.BlogCard
import com.kenshi.presentation.compose.ui.components.LoadStateFooter
import com.kenshi.presentation.compose.ui.theme.Gray300
import com.kenshi.presentation.item.blog.BlogItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BlogScreen(
    blogs: LazyPagingItems<BlogItem>,
    searchQuery: String,
    debouncedSearchQuery: String,
    onClickBlogDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val controller = LocalSoftwareKeyboardController.current

    val isInitial = searchQuery.isEmpty()
    val isEmpty = blogs.itemCount == 0
    val isLoading = blogs.loadState.refresh is LoadState.Loading
    val isError = blogs.loadState.refresh is LoadState.Error


    LaunchedEffect(key1 = debouncedSearchQuery) {
        listState.animateScrollToItem(0)
        controller?.hide()
    }

    when  {
        isInitial && isEmpty -> {
            InitialScreen()
        }

        isLoading -> {
            LoadingScreen()
        }

        isError -> {
            ErrorScreen(
                errorMessage = stringResource(id = R.string.blog_error_message),
                onClickRetryButton = { blogs.retry() },
            )
        }

        isEmpty -> {
            NoResultScreen()
        }

        else -> {
            LazyColumn(state = listState) {
                items(
                    count = blogs.itemCount,
                    key = blogs.itemKey(key = { blog -> blog.url }),
                    contentType = blogs.itemContentType()
                ) { index ->
                    val item = blogs[index]
                    item?.let {
                        BlogCard(blogItem = it, searchQuery, onClick = onClickBlogDetail)
                    }

                    Divider(color = Gray300)
                }

                item {
                    LoadStateFooter(
                        loadState = blogs.loadState.append,
                        onRetry = { blogs.retry() },
                    )
                }
            }
        }
    }
}