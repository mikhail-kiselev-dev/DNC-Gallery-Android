package com.dnc.androidgallery.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dnc.androidgallery.R

@Suppress("unused")
fun ImageView.loadImageUrl(url: String?, placeHolder: Int = R.drawable.ic_launcher_foreground) {
    Glide.with(this)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}
