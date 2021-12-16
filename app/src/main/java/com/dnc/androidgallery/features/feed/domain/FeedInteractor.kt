package com.dnc.androidgallery.features.feed.domain

import com.dnc.androidgallery.features.feed.data.datasource.FeedDataSource
import com.dnc.androidgallery.features.feed.domain.model.Photo

interface FeedInteractor {
    suspend fun getTopPhotos(page: Int, date: String?): List<Photo>
    suspend fun getRecentPhotos(page: Int, date: String?): List<Photo>
}

class FeedInteractorImpl(
    private val dataSource: FeedDataSource
) : FeedInteractor {
    override suspend fun getTopPhotos(page: Int, date: String?): List<Photo> {
        return dataSource.getTopPhotos(page, date)
    }

    override suspend fun getRecentPhotos(page: Int, date: String?): List<Photo> {
        return dataSource.getRecentPhotos(page, date)
    }
}
