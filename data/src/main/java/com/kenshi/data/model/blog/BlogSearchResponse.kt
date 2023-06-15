package com.kenshi.data.model.blog

import com.kenshi.data.model.video.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BlogSearchResponse(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("documents")
    val documents: List<Blog>,
)