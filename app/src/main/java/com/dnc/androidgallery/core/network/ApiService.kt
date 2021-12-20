package com.dnc.androidgallery.core.network

import com.dnc.androidgallery.features.details.data.response.PhotoResponse
import com.dnc.androidgallery.features.feed.data.response.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Suppress("unused")
interface ApiService {
    @GET("?method=flickr.interestingness.getList")
    suspend fun getInterestingPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 9,
        @Query("date") date: String? = ""
    ): FeedResponse

    @GET("?method=flickr.photos.getRecent")
    suspend fun getRecentPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 9,
    ): FeedResponse

    @GET("?method=flickr.photos.getInfo")
    suspend fun getPhotoInfo(
        @Query("photo_id") id: Long
    ): PhotoResponse
}
