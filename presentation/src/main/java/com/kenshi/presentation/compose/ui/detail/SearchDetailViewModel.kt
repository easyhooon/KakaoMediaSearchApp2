package com.kenshi.presentation.compose.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.WebViewState
import com.kenshi.presentation.compose.navigation.SearchDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val url: String = savedStateHandle[SearchDetail.urlTypeArg] ?: ""
    val webViewState = WebViewState(WebContent.Url(url = url))
    val webViewNavigator = WebViewNavigator(viewModelScope)
}