package com.dnc.androidgallery.features.feed.data.datasource

import com.dnc.androidgallery.core.network.ApiService

interface FeedInfoDataSource {
    suspend fun getTotalPagesTop(): Int
    suspend fun getTotalPagesRecent(): Int
}

class FeedInfoDataSourceImpl(private val apiService: ApiService) : FeedInfoDataSource {
    override suspend fun getTotalPagesTop(): Int {
        return apiService.getInterestingPhotos().content.pages
    }

    override suspend fun getTotalPagesRecent(): Int {
        return apiService.getRecentPhotos().content.pages
    }
}
