package com.kenshi.presentation.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme
import com.kenshi.presentation.item.video.VideoItem
import com.kenshi.presentation.util.formatPlaytime

@Composable
fun VideoCard(
    video: VideoItem,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(180.dp, 120.dp),
            model = ImageRequest.Builder(context)
                .data(video.thumbnail)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Video Thumbnail Image"
        )
        Text(
            modifier = Modifier
                .align(Alignment.Bottom)
                .offset(y = (-8).dp)
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .background(Color.Gray),
            text = formatPlaytime(video.playtime),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = video.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = video.datetime,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = video.author,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun VideoCardPreview() {
    KakaoMediaSearchApp2Theme {
        VideoCard(
            video = VideoItem(
                title = "",
                url = "",
                datetime = "",
                playtime = 0,
                author = "",
                thumbnail = "",
            ),
            onClick = {}
        )
    }
}