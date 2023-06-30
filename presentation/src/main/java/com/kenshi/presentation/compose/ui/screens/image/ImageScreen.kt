package com.kenshi.presentation.compose.ui.screens.image

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
import com.kenshi.presentation.compose.ui.components.ImageCard
import com.kenshi.presentation.item.image.ImageItem
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageScreen(
    searchQuery: String,
    images: LazyPagingItems<ImageItem>,
    onClickSeeImageDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = searchQuery) {
        snapshotFlow { images.loadState.refresh }
            .filter { it is LoadState.Loading }
            .collect {
                listState.animateScrollToItem(0)
                controller?.hide()
            }
    }

    LazyColumn(state = listState) {
        items(
            count = images.itemCount,
            key = images.itemKey(key = { image -> image.thumbnailUrl }),
            contentType = images.itemContentType()
        ) { index ->
            val item = images[index]
            item?.let {
                ImageCard(imageItem = it, onClick = onClickSeeImageDetail)
            }
        }
//        loadStateFooterWithRetry(
//            pagingItems = images,
//            onRetry = { images.retry() }
//        )
    }
}