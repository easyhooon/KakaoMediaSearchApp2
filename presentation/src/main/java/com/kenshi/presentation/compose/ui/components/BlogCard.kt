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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.util.extractDateFromDatetime

@Composable
fun BlogCard(
    blogItem: BlogItem,
    searchQuery: String,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(blogItem.url) }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(180.dp, 120.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(context)
                .data(blogItem.thumbnail)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Blog Thumbnail Image"
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            BlogTitleText(
                searchQuery = searchQuery, title = blogItem.title
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = extractDateFromDatetime(blogItem.datetime),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = blogItem.blogName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun BlogTitleText(
    searchQuery: String,
    title: String
) {
    val plainText = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    val words = plainText.split(" ")
    Text(
        text = buildAnnotatedString {
            words.forEachIndexed { index, word ->
                if (word.contains(searchQuery, true)) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    ) {
                        append(word)
                    }
                } else {
                    append(word)
                }
                if (index != words.size - 1) append(" ")
            }
        },
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = Color.DarkGray
    )
}

@Preview
@Composable
fun BlogCardPreview() {
    KakaoMediaSearchApp2Theme {
        BlogCard(
            blogItem = BlogItem(
                title = "Android is fun",
                contents = "",
                url = "",
                blogName = "",
                thumbnail = "",
                datetime = "",
            ),
            searchQuery = "Android",
            onClick = {}
        )
    }
}