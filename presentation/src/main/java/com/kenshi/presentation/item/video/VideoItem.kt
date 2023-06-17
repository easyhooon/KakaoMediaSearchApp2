package com.kenshi.presentation.item.video

data class VideoItem(
    val title: String,
    val url: String,
    val datetime: String,
    val playtime: Int,
    val author: String,
    val thumbnail: String,
)