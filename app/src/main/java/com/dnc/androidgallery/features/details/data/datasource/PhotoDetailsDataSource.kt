package com.dnc.androidgallery.features.details.data.datasource

import com.dnc.androidgallery.core.network.ApiService
import com.dnc.androidgallery.features.details.domain.model.Details
import com.dnc.androidgallery.features.details.domain.model.domain

interface PhotoDetailsDataSource {
    suspend fun getInfo(id: Long): Details
}

class PhotoDetailsDataSourceImpl(private val apiService: ApiService) : PhotoDetailsDataSource {
    override suspend fun getInfo(id: Long): Details {
        return apiService.getPhotoInfo(id).domain()
    }
}
