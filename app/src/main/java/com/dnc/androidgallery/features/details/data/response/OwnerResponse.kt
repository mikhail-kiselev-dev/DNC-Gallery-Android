package com.dnc.androidgallery.features.details.data.response

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("username")
    val username: String,
    @SerializedName("location")
    val location: String?
)
