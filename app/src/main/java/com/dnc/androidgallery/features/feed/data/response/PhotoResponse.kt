package com.dnc.androidgallery.features.feed.data.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("farm")
    val farm: String,
    @SerializedName("title")
    val title: String?,
)
