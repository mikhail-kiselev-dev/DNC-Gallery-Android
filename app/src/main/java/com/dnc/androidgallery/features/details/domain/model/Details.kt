package com.dnc.androidgallery.features.details.domain.model

import com.dnc.androidgallery.features.details.data.response.PhotoResponse

data class Details(
    val id: Long,
    val url: String,
    val username: String,
    val location: String,
    val title: String,
    val description: String,
    val taken: String,
    val views: String,
)

fun PhotoResponse.domain() = Details(
    id = info.id,
    url = "https://farm${info.farm}.staticflickr.com/${info.server}/${info.id}_${info.secret}.jpg",
    username = info.owner.username,
    location = info.owner.location ?: "",
    title = info.title?.data ?: "",
    description = info.description?.data ?: "",
    taken = info.dates?.taken ?: "",
    views = info.views
)
