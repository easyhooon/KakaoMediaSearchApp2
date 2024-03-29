package com.kenshi.presentation

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
import com.kenshi.presentation.util.Constants.SEARCH_TIME_DELAY
import com.kenshi.presentation.util.SaveableMutableStateFlow
import com.kenshi.presentation.util.getMutableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getBlogSearchListUseCase: GetBlogSearchListUseCase,
    private val getImageSearchListUseCase: GetImageSearchListUseCase,
    private val getVideoSearchListUseCase: GetVideoSearchListUseCase,
    getSortModeUseCase: GetSortModeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchQuery: SaveableMutableStateFlow<String> =
        savedStateHandle.getMutableStateFlow(
            KEY_SEARCH_QUERY, ""
        )
    val searchQuery = _searchQuery.asStateFlow()

    val debouncedSearchQuery: Flow<String?> = searchQuery
        .debounce(SEARCH_TIME_DELAY)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged()

    private val _refreshClickEvent = MutableSharedFlow<Unit>()
    val refreshClickEvent: SharedFlow<Unit> = _refreshClickEvent.asSharedFlow()

    fun refresh() {
        viewModelScope.launch {
            _refreshClickEvent.emit(Unit)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val searchSortMode: StateFlow<String> =
        getSortModeUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ""
            )

    val searchBlogs: Flow<PagingData<BlogItem>> =
        debouncedSearchQuery.filterNotNull()
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

    val searchVideos: Flow<PagingData<VideoItem>> =
        debouncedSearchQuery.filterNotNull()
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

    val searchImages: Flow<PagingData<ImageItem>> =
        debouncedSearchQuery.filterNotNull()
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

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
    }
}