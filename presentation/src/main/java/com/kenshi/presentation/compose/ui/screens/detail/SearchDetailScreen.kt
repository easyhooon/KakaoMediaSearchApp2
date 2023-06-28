package com.kenshi.presentation.compose.ui.screens.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import timber.log.Timber

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SearchDetailScreen() {
    val viewModel: SearchDetailViewModel = hiltViewModel()
    val webViewState = viewModel.webViewState
    val webViewNavigator = viewModel.webViewNavigator
    val webChromeClient = AccompanistWebChromeClient()

    val loadingState = webViewState.loadingState
    if (loadingState is LoadingState.Loading) {
        LinearProgressIndicator(
            progress = loadingState.progress,
            modifier = Modifier.fillMaxWidth()
        )
    }

    val webViewClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Timber.tag("Accompanist WebView").d("Page started loading for %s", url)
            }
        }
    }

    WebView(
        state = webViewState,
        navigator = webViewNavigator,
        onCreated = { webView ->
            with(webView) {
                settings.run {
                    javaScriptEnabled = true
                }
            }
        },
        client = webViewClient,
        chromeClient = webChromeClient,
    )
}