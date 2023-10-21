package com.kenshi.presentation.compose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.kenshi.presentation.compose.ui.screens.EndOfResultScreen
import com.kenshi.presentation.compose.ui.screens.LoadErrorScreen
import com.kenshi.presentation.compose.ui.screens.LoadingScreen

@Composable
fun LoadStateFooter(
    modifier: Modifier = Modifier,
    loadState: LoadState,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        when (loadState) {
            is LoadState.Loading -> LoadingScreen()

            is LoadState.Error -> LoadErrorScreen(onRetryClick = onRetryClick)

            else -> EndOfResultScreen()
        }
    }
}
