package com.dnc.androidgallery.features.details.domain

import com.dnc.androidgallery.features.details.data.datasource.PhotoDetailsDataSource
import com.dnc.androidgallery.features.details.domain.model.Details

interface PhotoDetailsInteractor {
    suspend fun getInfo(id: Long): Details
}

class PhotoDetailsInteractorImpl(
    private val dataSource: PhotoDetailsDataSource
) : PhotoDetailsInteractor {
    override suspend fun getInfo(id: Long): Details {
        return dataSource.getInfo(id)
    }
}
