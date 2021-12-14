package com.dnc.androidgallery.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dnc.androidgallery.R
import com.dnc.androidgallery.core.data.Image

@Suppress("unused")
fun ImageView.loadImageUrl(url: String?, placeHolder: Int = R.drawable.ic_launcher_foreground) {
    Glide.with(this)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}

fun ImageView.loadImage(
    image: Image,
) {
    val glide = Glide.with(this)
    val builder = when (image) {
        is Image.ImgRes -> glide.load(image.resId)
        is Image.ImgUrl -> glide.load(image.url)
    }
    builder.into(this)
}
