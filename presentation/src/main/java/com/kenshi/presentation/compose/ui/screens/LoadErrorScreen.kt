package com.kenshi.presentation.compose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenshi.presentation.R
import com.kenshi.presentation.compose.ui.theme.Gray900

@Composable
fun LoadErrorScreen(
  modifier: Modifier = Modifier,
  onRetryClick: () -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp),
    contentAlignment = Alignment.Center,
  ) {
    Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = stringResource(id = R.string.error_message),
        color = Gray900,
        fontSize = 16.sp,
        modifier = Modifier.align(Alignment.CenterVertically),
      )
      Button(
        onClick = onRetryClick,
      ) {
        Text(text = stringResource(id = R.string.retry))
      }
    }
  }
}
