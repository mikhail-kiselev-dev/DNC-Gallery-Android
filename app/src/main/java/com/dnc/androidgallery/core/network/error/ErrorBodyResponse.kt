package com.dnc.androidgallery.core.network.error

import com.google.gson.annotations.SerializedName

data class ErrorBodyResponse(
    @SerializedName("errors")
    val errors: Map<String, Any>?,
    @SerializedName("message")
    val message: String?
)
