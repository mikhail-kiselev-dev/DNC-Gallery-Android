package com.dnc.androidgallery.features.details.ui.model

import com.dnc.androidgallery.features.details.domain.model.Details

data class DetailsItem(
    val id: Long,
    val url: String,
    val author: String,
    val location: String,
    val title: String,
    val description: String,
    val taken: String,
    val views: String,
)

fun Details.toItem() = DetailsItem(
    id = id,
    url = url,
    author = "Taken by $username",
    location = location,
    title = title,
    description = description,
    taken = "Taken $taken",
    views = "Views: $views",
)
