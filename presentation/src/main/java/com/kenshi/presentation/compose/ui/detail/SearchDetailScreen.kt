package com.kenshi.presentation.compose.ui.detail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme
import timber.log.Timber

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SearchDetailScreen(
    url: String?
) {
    Timber.tag("SearchDetailScreen").d(url)
    // val viewModel: SearchDetailViewModel = hiltViewModel()
    // val webViewState = viewModel.webViewState
    // val webViewNavigator = viewModel.webViewNavigator
    val webViewState = rememberWebViewState(
        url = url ?: "",
        additionalHttpHeaders = emptyMap()
    )
    val webViewNavigator = rememberWebViewNavigator()
    val webViewClient = AccompanistWebViewClient()
    val webChromeClient = AccompanistWebChromeClient()

    WebView(
        state = webViewState,
        navigator = webViewNavigator,
        client = webViewClient,
        chromeClient = webChromeClient,
        onCreated = { webView ->
            with(webView) {
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    javaScriptCanOpenWindowsAutomatically = false
                }
            }
        }
    )
    BackHandler(enabled = true) {
        if (webViewNavigator.canGoBack) {
            webViewNavigator.navigateBack()
        }
    }
}

@Preview
@Composable
fun SearchDetailScreenPreview() {
    KakaoMediaSearchApp2Theme {
        SearchDetailScreen(
            url = "m.naver.com"
        )
    }
}