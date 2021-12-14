package com.dnc.androidgallery.features.feed.ui.model

import com.dnc.androidgallery.base.recycler.ItemModel
import com.dnc.androidgallery.core.data.Image
import com.dnc.androidgallery.features.feed.domain.model.Photo

data class FeedItemModel(
    val title: String,
    val image: Image,
) : ItemModel

fun Photo.itemModel() = FeedItemModel(
    title = title,
    image = Image.ImgUrl(url)
)
