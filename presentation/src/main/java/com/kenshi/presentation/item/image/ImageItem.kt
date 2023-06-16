package com.kenshi.presentation.item.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItem (
    val collection: String,
    val thumbnailUrl: String,
    val width: String,
    val height: String,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: String,
): Parcelable