package com.dnc.androidgallery.features.feed.domain

import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.features.feed.data.datasource.FeedInfoDataSource

interface FeedInfoInteractor {
    suspend fun getTotalPagesTop(): Int
    suspend fun getTotalPagesRecent(): Int
    suspend fun getTotalPhotosTop(): Int
    suspend fun getTotalPhotosRecent(): Int
    suspend fun getPhotoId(feed: FeedType, position: Int): Long
    suspend fun getRecentPhotosId(): List<Long>
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

    override suspend fun getTotalPhotosTop(): Int {
        return dataSource.getTotalPhotosTop()
    }

    override suspend fun getTotalPhotosRecent(): Int {
        return dataSource.getTotalPhotosRecent()
    }

    override suspend fun getPhotoId(feed: FeedType, position: Int): Long {
        return dataSource.getPhotoId(feed, position)
    }

    override suspend fun getRecentPhotosId(): List<Long> {
        val firstList = dataSource.getRecentPhotosId(1)
        val secondList = dataSource.getRecentPhotosId(2)
        return firstList + secondList
    }
}
