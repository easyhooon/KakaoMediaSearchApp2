package com.kenshi.presentation.compose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme

@Composable
fun LoadStateFooter(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetryClick: () -> Unit,
    isLoading: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Button(
                    onClick = { onRetryClick() }
                ) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Preview
@Composable
fun LoadStateFooterPreview() {
    KakaoMediaSearchApp2Theme {
        LoadStateFooter(
            errorMessage = "문제가 발생하였습니다",
            onRetryClick = {},
            isLoading = false
        )
    }
}