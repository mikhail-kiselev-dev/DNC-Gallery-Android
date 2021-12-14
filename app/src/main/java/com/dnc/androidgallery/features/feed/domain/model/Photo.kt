package com.dnc.androidgallery.features.feed.domain.model

import com.dnc.androidgallery.features.feed.data.response.PhotoResponse

data class Photo(
    val id: Long,
    val title: String,
    val url: String,
)

fun PhotoResponse.domain() = Photo(
    id = id,
    title = title ?: "",
    url = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
)
