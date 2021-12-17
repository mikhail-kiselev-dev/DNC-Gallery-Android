package com.dnc.androidgallery.features.feed.data.datasource

import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.core.network.ApiService

interface FeedInfoDataSource {
    suspend fun getTotalPagesTop(): Int
    suspend fun getTotalPagesRecent(): Int
    suspend fun getTotalPhotosTop(): Int
    suspend fun getTotalPhotosRecent(): Int
    suspend fun getPhotoId(feed: FeedType, position: Int): Long
    suspend fun getRecentPhotosId(page: Int): List<Long>
}

class FeedInfoDataSourceImpl(private val apiService: ApiService) : FeedInfoDataSource {
    override suspend fun getTotalPagesTop(): Int {
        return apiService.getInterestingPhotos().content.pages
    }

    override suspend fun getTotalPagesRecent(): Int {
        return apiService.getRecentPhotos().content.pages
    }

    override suspend fun getTotalPhotosTop(): Int {
        return apiService.getInterestingPhotos().content.total
    }

    override suspend fun getTotalPhotosRecent(): Int {
        return apiService.getRecentPhotos().content.total
    }

    override suspend fun getPhotoId(feed: FeedType, position: Int): Long {
        return when (feed) {
            FeedType.TOP -> {
                apiService.getInterestingPhotos(page = position, perPage = 1).content.photos[0].id
            }
            FeedType.RECENT -> {
                apiService.getRecentPhotos(page = position, perPage = 1).content.photos[0].id
            }
        }
    }

    override suspend fun getRecentPhotosId(page: Int): List<Long> {
        val maxPerPageValue = 500
        return apiService.getRecentPhotos(page = page, perPage = maxPerPageValue).content.photos.map {
            it.id
        }
    }
}
