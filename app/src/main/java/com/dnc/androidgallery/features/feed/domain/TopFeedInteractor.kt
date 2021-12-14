package com.dnc.androidgallery.features.feed.domain

import com.dnc.androidgallery.features.feed.data.datasource.TopFeedDataSource
import com.dnc.androidgallery.features.feed.domain.model.Photo

interface TopFeedInteractor {
    suspend fun getTopPhotos(page: Int, date: String?): List<Photo>
    suspend fun getTotalPages(): Int
}

class TopFeedInteractorImpl(
    private val dataSource: TopFeedDataSource
) : TopFeedInteractor {
    override suspend fun getTopPhotos(page: Int, date: String?): List<Photo> {
        return dataSource.getTopPhotos(page, date)
    }
    override suspend fun getTotalPages(): Int {
        return dataSource.getTotalPages()
    }
}
