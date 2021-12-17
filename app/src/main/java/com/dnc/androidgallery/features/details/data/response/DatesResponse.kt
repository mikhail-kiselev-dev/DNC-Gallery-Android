package com.dnc.androidgallery.features.details.data.response

import com.google.gson.annotations.SerializedName

data class DatesResponse(
    @SerializedName("taken")
    val taken: String?
)
