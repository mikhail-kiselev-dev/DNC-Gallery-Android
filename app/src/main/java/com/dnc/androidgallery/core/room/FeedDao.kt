package com.dnc.androidgallery.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dnc.androidgallery.features.feed.data.entity.FeedEntity

@Dao
interface FeedDao {

    @Query("SELECT * FROM FeedEntity")
    suspend fun getFeed(): List<FeedEntity>

    @Query("DELETE FROM FeedEntity")
    suspend fun clearDb()

    @Insert
    suspend fun insertAll(vararg feed: FeedEntity)
}
