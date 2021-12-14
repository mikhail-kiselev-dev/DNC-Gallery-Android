package com.dnc.androidgallery.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar

private const val SCALE = 0.8f

class LoadingProgressBar(
    context: Context,
    attrs: AttributeSet?
) : ProgressBar(context, attrs) {

    init {
        this.scaleX = SCALE
        this.scaleY = SCALE
    }
}
