package com.kenshi.presentation.compose.ui.video

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.kenshi.presentation.compose.ui.components.VideoCard
import com.kenshi.presentation.item.video.VideoItem

@Composable
fun VideoScreen(
    videos: LazyPagingItems<VideoItem>,
    onClickSeeVideoDetail: (String) -> Unit = {}
) {
    LazyColumn {
        items(
            items = videos,
            key = { video ->
                video.url
            }
        ) { video ->
            video?.let {
                VideoCard(videoItem = it, onClick = onClickSeeVideoDetail)
            }
        }
    }
}