package com.dnc.androidgallery.features.feed.data.datasource

import com.dnc.androidgallery.core.network.ApiService
import com.dnc.androidgallery.features.feed.domain.model.Photo
import com.dnc.androidgallery.features.feed.domain.model.domain

interface FeedDataSource {
    suspend fun getTopPhotos(page: Int, date: String?): List<Photo>
    suspend fun getRecentPhotos(page: Int): List<Photo>
}

class FeedDataSourceImpl(private val apiService: ApiService) : FeedDataSource {
    override suspend fun getTopPhotos(page: Int, date: String?): List<Photo> {
        return apiService.getInterestingPhotos(page = page, date = date).content.photos.map {
            it.domain()
        }
    }

    override suspend fun getRecentPhotos(page: Int): List<Photo> {
        return apiService.getRecentPhotos(page = page).content.photos.map {
            it.domain()
        }
    }
}
