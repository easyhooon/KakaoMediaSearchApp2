package com.kenshi.presentation.compose.ui.image

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kenshi.presentation.compose.ui.components.ImageCard
import com.kenshi.presentation.item.image.ImageItem

@Composable
fun ImageScreen(
    images: LazyPagingItems<ImageItem>,
    onClickSeeImageDetail: (String) -> Unit,
) {
    LazyColumn {
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
    }
}