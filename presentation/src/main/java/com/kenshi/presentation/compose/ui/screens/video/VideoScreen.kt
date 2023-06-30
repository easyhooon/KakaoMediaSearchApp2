package com.kenshi.presentation.compose.ui.screens.video

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
import com.kenshi.presentation.compose.ui.components.VideoCard
import com.kenshi.presentation.item.video.VideoItem
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VideoScreen(
    searchQuery: String,
    videos: LazyPagingItems<VideoItem>,
    onClickSeeVideoDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val controller = LocalSoftwareKeyboardController.current

    // images 는 동일한 인스턴스이기 때문에 변하지 않음
    // searchKeyword 를 key 로 사용해야 함
    // String 은 immutable 타입이기 때문에 한번 생성된 문자열은 변경될 수 없으며, 문자열을 수정하면 새로운 문자열이 생성됨
    LaunchedEffect(key1 = searchQuery) {
        snapshotFlow { videos.loadState.refresh }
            .filter { it is LoadState.Loading }
            .collect {
                listState.animateScrollToItem(0)
                // 키보드 내리기
                controller?.hide()
            }
    }

    LazyColumn(state = listState) {
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
//        loadStateFooterWithRetry(
//            pagingItems = videos,
//            onRetry = { videos.retry() }
//        )
    }
}