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
import com.kenshi.presentation.compose.ui.components.LoadStateFooter
import com.kenshi.presentation.compose.ui.components.VideoCard
import com.kenshi.presentation.compose.ui.theme.Gray300
import com.kenshi.presentation.item.video.VideoItem

//TODO 첫번째 검색 이후 키보드가 내려가지 않음
// 이후 검색어를 변경하면 키보드가 정상적으로 내려감
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VideoScreen(
    videos: LazyPagingItems<VideoItem>,
    searchQuery: String,
    debouncedSearchQuery: String,
    onClickVideoDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val controller = LocalSoftwareKeyboardController.current

    val isInitial = searchQuery.isEmpty()
    val isEmpty = videos.itemCount == 0
    val isLoading = videos.loadState.refresh is LoadState.Loading
    val isError = videos.loadState.refresh is LoadState.Error

    // 트리거 되지 않음
//    LaunchedEffect(key1 = videos) {
//        listState.animateScrollToItem(0)
//        controller?.hide()
//    }

    // videos 는 값이 변해도 인스턴스가 동일하기 때문에 변하지 않음
    // debouncedSearchQuery 를 key 로 사용해야 함
    // String 은 immutable 타입이기 때문에 한번 생성된 문자열은 변경될 수 없으며, 문자열을 수정하면 새로운 문자열이 생성됨
    LaunchedEffect(key1 = debouncedSearchQuery) {
        listState.animateScrollToItem(0)
        controller?.hide()
    }

    when {
        isInitial && isEmpty -> {
            InitialScreen()
        }

        isLoading -> {
            LoadingScreen()
        }

        isError -> {
            NetworkErrorScreen(
                errorMessage = stringResource(id = R.string.video_error_message),
                onClickRetryButton = { videos.retry() },
            )
        }

        else -> {
            LazyColumn(state = listState) {
                items(
                    count = videos.itemCount,
                    key = videos.itemKey(key = { video -> video.url }),
                    contentType = videos.itemContentType()
                ) { index ->
                    val item = videos[index]
                    item?.let {
                        VideoCard(videoItem = it, onClick = onClickVideoDetail)
                    }

                    Divider(color = Gray300)
                }

                item {
                    LoadStateFooter(
                        loadState = videos.loadState.append,
                        onRetryClick = { videos.retry() },
                    )
                }
            }
        }
    }
}