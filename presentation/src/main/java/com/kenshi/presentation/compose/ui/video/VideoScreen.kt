package com.kenshi.presentation.compose.ui.video

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kenshi.presentation.compose.ui.components.VideoCard
import com.kenshi.presentation.item.video.VideoItem

@Composable
fun VideoScreen(
    videos: LazyPagingItems<VideoItem>,
    onClickSeeVideoDetail: (String) -> Unit,
) {
    LazyColumn {
        items(
            count = videos.itemCount,
            key = videos.itemKey(key = { video -> video.url }),
            contentType = videos.itemContentType()
        ) { index ->
            val item = videos[index]
            item?.let {
                VideoCard(videoItem = it, onClick = onClickSeeVideoDetail)
            }
        }
    }
}