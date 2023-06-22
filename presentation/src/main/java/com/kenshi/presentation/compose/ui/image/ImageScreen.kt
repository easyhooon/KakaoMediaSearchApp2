package com.kenshi.presentation.compose.ui.image

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kenshi.presentation.compose.ui.components.ImageCard
import com.kenshi.presentation.item.image.ImageItem

@Composable
fun ImageScreen(
    images: LazyPagingItems<ImageItem>,
    onClickSeeImageDetail: (String) -> Unit = {},
) {
    LazyColumn {
        items(
            items = images,
            key = { image ->
                image.thumbnailUrl
            }
        ) { image ->
            image?.let {
                ImageCard(imageItem = it, onClick = onClickSeeImageDetail)
            }
        }
    }
}