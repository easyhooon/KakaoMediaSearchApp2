package com.kenshi.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kenshi.domain.usecase.GetBlogSearchListUseCase
import com.kenshi.domain.usecase.GetImageSearchListUseCase
import com.kenshi.domain.usecase.GetSortModeUseCase
import com.kenshi.domain.usecase.GetVideoSearchListUseCase
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.item.video.VideoItem
import com.kenshi.presentation.mapper.toItem
import com.kenshi.presentation.util.SaveableMutableStateFlow
import com.kenshi.presentation.util.getMutableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getBlogSearchListUseCase: GetBlogSearchListUseCase,
    private val getImageSearchListUseCase: GetImageSearchListUseCase,
    private val getVideoSearchListUseCase: GetVideoSearchListUseCase,
    getSortModeUseCase: GetSortModeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query: SaveableMutableStateFlow<String?> = savedStateHandle.getMutableStateFlow(KEY_SEARCH_TEXT, null)
    val query = _query.asStateFlow()

    private val searchSortMode: StateFlow<String> =
        getSortModeUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ""
            )

    // TODO: 흐름 완벽히 이해
    val searchBlogs: Flow<PagingData<BlogItem>> =
        query.filterNotNull()
            .combineTransform(searchSortMode) { query, sortMode -> emit(query to sortMode) }
            .flatMapLatest { (query, sortMode) ->
                getBlogSearchListUseCase(query, sortMode)
                    .map { pagingData ->
                        pagingData.map { blogEntity ->
                            blogEntity.toItem()
                        }
                    }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                PagingData.empty()
            )

    val searchVideos: Flow<PagingData<VideoItem>> =
        query.filterNotNull()
            .combineTransform(searchSortMode) { query, sortMode -> emit(query to sortMode) }
            .flatMapLatest { (query, sortMode) ->
                getVideoSearchListUseCase(query, sortMode)
                    .map { pagingData ->
                        pagingData.map { videoEntity ->
                            videoEntity.toItem()
                        }
                    }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                PagingData.empty()
            )

    val searchImages: Flow<PagingData<ImageItem>> =
        query.filterNotNull()
            .combineTransform(searchSortMode) { query, sortMode -> emit(query to sortMode) }
            .flatMapLatest { (query, sortMode) ->
                getImageSearchListUseCase(query, sortMode)
                    .map { pagingData ->
                        pagingData.map { imageEntity ->
                            imageEntity.toItem()
                        }
                    }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                PagingData.empty()
            )

    fun setQuery(query: String) {
        _query.value = query
    }

    companion object {
        private const val KEY_SEARCH_TEXT = "search_text"
    }
}