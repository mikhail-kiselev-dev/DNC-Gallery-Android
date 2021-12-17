package com.dnc.androidgallery.features.details.data.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("photo")
    val info: InfoResponse
)
