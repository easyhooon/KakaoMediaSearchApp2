package com.kenshi.domain.entity.image

data class ImageEntity(
    val collection: String,
    val thumbnailUrl: String,
    val width: String,
    val height: String,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: String,
)