package com.dnc.androidgallery.core.network

import com.dnc.androidgallery.features.feed.data.response.TopFeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Suppress("unused")
interface ApiService {
    @GET("?method=flickr.interestingness.getList")
    suspend fun getInterestingPhotos(
        @Query("page") page: Int = 1,
        @Query("date") date: String? = ""
    ): TopFeedResponse
}
