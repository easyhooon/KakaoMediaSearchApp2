package com.kenshi.presentation.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.util.extractDateFromDatetime

@Composable
fun ImageCard(
    imageItem: ImageItem,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(imageItem.thumbnailUrl) }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(180.dp, 120.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(context)
                .data(imageItem.thumbnailUrl)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Image Thumbnail Image"
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = imageItem.collection,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.DarkGray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = extractDateFromDatetime(imageItem.datetime),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = imageItem.displaySiteName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun ImageCardPreview() {
    KakaoMediaSearchApp2Theme {
        ImageCard(
            imageItem = ImageItem(
                collection = "",
                thumbnailUrl = "",
                width = "",
                height = "",
                displaySiteName = "",
                docUrl = "",
                datetime = "",
            ),
            onClick = {}
        )
    }
}