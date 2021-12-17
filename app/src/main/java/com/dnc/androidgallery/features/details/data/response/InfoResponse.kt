package com.dnc.androidgallery.features.details.data.response

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("owner")
    val owner: OwnerResponse,
    @SerializedName("title")
    val title: TitleResponse?,
    @SerializedName("description")
    val description: DescriptionResponse?,
    @SerializedName("dates")
    val dates: DatesResponse?,
    @SerializedName("views")
    val views: String,
    @SerializedName("farm")
    val farm: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("secret")
    val secret: String
)
