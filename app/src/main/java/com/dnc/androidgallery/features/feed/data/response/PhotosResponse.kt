package com.dnc.androidgallery.features.feed.data.response

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("photo")
    val photos: List<PhotoResponse>
)
