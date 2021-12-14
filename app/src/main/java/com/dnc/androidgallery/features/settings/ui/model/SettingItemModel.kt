package com.dnc.androidgallery.features.settings.ui.model

import com.dnc.androidgallery.base.recycler.ItemModel
import com.dnc.androidgallery.core.data.Image

data class SettingItemModel(
    val title: String,
    val image: Image,
    val setting: Settings,
) : ItemModel
