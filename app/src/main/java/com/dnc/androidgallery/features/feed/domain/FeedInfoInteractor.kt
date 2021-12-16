package com.dnc.androidgallery.features.feed.domain

import com.dnc.androidgallery.features.feed.data.datasource.FeedInfoDataSource

interface FeedInfoInteractor {
    suspend fun getTotalPagesTop(): Int
    suspend fun getTotalPagesRecent(): Int
}

class FeedInfoInteractorImpl(
    private val dataSource: FeedInfoDataSource
) : FeedInfoInteractor {
    override suspend fun getTotalPagesTop(): Int {
        return dataSource.getTotalPagesTop()
    }

    override suspend fun getTotalPagesRecent(): Int {
        return dataSource.getTotalPagesRecent()
    }
}
