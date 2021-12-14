package com.dnc.androidgallery.features.feed.data.response

import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("photos")
    val content: PhotosResponse
)
