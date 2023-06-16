package com.kenshi.presentation.item.video

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoItem(
    val title: String,
    val url: String,
    val datetime: String,
    val playtime: Int,
    val author: String,
    val thumbnail: String,
) : Parcelable