package com.kenshi.presentation.util

import androidx.compose.foundation.lazy.LazyListScope
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.kenshi.presentation.compose.ui.components.LoadStateFooter

fun <T : Any> LazyListScope.loadStateFooterWithRetry(
    pagingItems: LazyPagingItems<T>,
    onRetry: () -> Unit
) {
    when {
        pagingItems.loadState.refresh is LoadState.Loading -> {
            item {
                LoadStateFooter(
                    errorMessage = "",
                    onRetryClick = {},
                    isLoading = true
                )
            }
        }

        pagingItems.loadState.append is LoadState.Loading -> {
            item {
                LoadStateFooter(
                    errorMessage = "",
                    onRetryClick = {},
                    isLoading = true
                )
            }
        }

        pagingItems.loadState.refresh is LoadState.Error -> {
            val e = pagingItems.loadState.refresh as LoadState.Error
            item {
                LoadStateFooter(
                    errorMessage = e.error.message.toString(),
                    onRetryClick = onRetry,
                    isLoading = false
                )
            }
        }

        pagingItems.loadState.append is LoadState.Error -> {
            val e = pagingItems.loadState.append as LoadState.Error
            item {
                LoadStateFooter(
                    errorMessage = e.error.message.toString(),
                    onRetryClick = onRetry,
                    isLoading = false
                )
            }
        }
    }
}