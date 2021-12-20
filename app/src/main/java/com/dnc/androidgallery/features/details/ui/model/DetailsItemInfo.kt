package com.dnc.androidgallery.features.details.ui.model

import android.os.Parcelable
import com.dnc.androidgallery.core.data.FeedType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsItemInfo(
    val id: Long,
    val page: Int,
    val position: Int,
    val content: FeedType,
) : Parcelable
