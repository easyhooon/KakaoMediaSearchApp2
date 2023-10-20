package com.kenshi.presentation.extensions

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDivider(@ColorRes colorRes: Int, orientation: Int = DividerItemDecoration.VERTICAL) {
    val colorDrawable = ColorDrawable(ContextCompat.getColor(context, colorRes))
    val dividerItemDecoration = DividerItemDecoration(context, orientation)
    dividerItemDecoration.setDrawable(colorDrawable)
    addItemDecoration(dividerItemDecoration)
}
