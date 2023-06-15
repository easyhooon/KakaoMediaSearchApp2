package com.kenshi.data.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("collection")
    val collection: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("width")
    val width: String,
    @SerialName("height")
    val height: String,
    @SerialName("display_sitename")
    val displaySiteName: String,
    @SerialName("doc_url")
    val docUrl: String,
    @SerialName("datetime")
    val datetime: String,
)
