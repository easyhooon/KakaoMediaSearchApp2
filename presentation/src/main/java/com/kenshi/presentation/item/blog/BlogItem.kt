package com.kenshi.presentation.item.blog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BlogItem(
    val title: String,
    val contents: String,
    val url: String,
    val blogName: String,
    val thumbnail: String,
    val datetime: String,
): Parcelable
