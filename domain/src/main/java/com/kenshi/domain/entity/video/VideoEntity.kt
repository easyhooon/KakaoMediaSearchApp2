package com.kenshi.domain.entity.video


data class VideoEntity(
    val title: String,
    val url: String,
    val datetime: String,
    val playtime: Int,
    val author: String,
    val thumbnail: String,
)