package com.kenshi.presentation.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KakaoMediaSearchApp2Theme {
                KakaoMediaSearchApp()
            }
        }
    }
}