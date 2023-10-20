package com.kenshi.presentation.util

import android.text.Html
import android.text.SpannableString


fun makeSearchTextBold(text: String): SpannableString {
    return SpannableString(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT))
}

fun extractDateFromDatetime(datetime: String): String {
    return if (datetime.isNotEmpty()) datetime.substring(0, 10) else ""
}

fun formatPlaytime(timeInSeconds: Int): String {
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
