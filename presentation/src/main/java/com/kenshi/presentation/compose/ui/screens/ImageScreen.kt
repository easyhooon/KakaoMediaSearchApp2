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
import com.kenshi.presentation.compose.ui.components.ImageCard
import com.kenshi.presentation.compose.ui.components.LoadStateFooter
import com.kenshi.presentation.compose.ui.theme.Gray300
import com.kenshi.presentation.item.image.ImageItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageScreen(
    images: LazyPagingItems<ImageItem>,
    searchQuery: String,
    debouncedSearchQuery: String,
    onClickImageDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val controller = LocalSoftwareKeyboardController.current

    val isInitial = searchQuery.isEmpty()
    val isEmpty = images.itemCount == 0
    val isLoading = images.loadState.refresh is LoadState.Loading
    val isError = images.loadState.refresh is LoadState.Error

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
                errorMessage = stringResource(id = R.string.image_error_message),
                onClickRetryButton = { images.retry() },
            )
        }

        else -> {
            LazyColumn(state = listState) {
                items(
                    count = images.itemCount,
                    key = images.itemKey(key = { image -> image.thumbnailUrl }),
                    contentType = images.itemContentType()
                ) { index ->
                    val item = images[index]
                    item?.let {
                        ImageCard(imageItem = it, onClick = onClickImageDetail)
                    }

                    Divider(color = Gray300)
                }

                item {
                    LoadStateFooter(
                        loadState = images.loadState.append,
                        onRetry = { images.retry() },
                    )
                }
            }
        }
    }
}