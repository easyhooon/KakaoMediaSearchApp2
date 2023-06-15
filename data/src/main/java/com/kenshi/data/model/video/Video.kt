package com.kenshi.data.model.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
    @SerialName("datetime")
    val datetime: String,
    @SerialName("play_time")
    val playtime: Int,
    @SerialName("author")
    val author: String,
    @SerialName("thumbnail")
    val thumbnail: String,
)