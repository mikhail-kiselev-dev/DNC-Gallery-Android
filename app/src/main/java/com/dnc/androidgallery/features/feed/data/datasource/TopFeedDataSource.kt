package com.dnc.androidgallery.features.feed.data.datasource

import com.dnc.androidgallery.core.network.ApiService
import com.dnc.androidgallery.features.feed.domain.model.Photo
import com.dnc.androidgallery.features.feed.domain.model.domain

interface TopFeedDataSource {
    suspend fun getTopPhotos(page: Int, date: String?): List<Photo>
    suspend fun getTotalPages(): Int
}

class TopFeedDataSourceImpl(private val apiService: ApiService) : TopFeedDataSource {
    override suspend fun getTopPhotos(page: Int, date: String?): List<Photo> {
        return apiService.getInterestingPhotos(page, date).content.photos.map {
            it.domain()
        }
    }

    override suspend fun getTotalPages(): Int {
        return apiService.getInterestingPhotos().content.pages
    }
}
