package com.dnc.androidgallery.core.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Image : Parcelable {
    @Parcelize
    class ImgRes(val resId: Int) : Image()

    @Parcelize
    class ImgUrl(val url: String) : Image()
}

fun Image.entity(): String {
    return when (this) {
        is Image.ImgRes -> resId.toString()
        is Image.ImgUrl -> url
    }
}
