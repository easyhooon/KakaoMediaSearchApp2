package com.kenshi.data.model.blog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Blog(
    @SerialName("title")
    val title: String,
    @SerialName("contents")
    val contents: String,
    @SerialName("url")
    val url: String,
    @SerialName("blogname")
    val blogName: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("datetime")
    val datetime: String,
)