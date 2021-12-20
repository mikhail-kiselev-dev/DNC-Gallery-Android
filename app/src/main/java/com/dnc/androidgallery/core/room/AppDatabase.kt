package com.dnc.androidgallery.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dnc.androidgallery.features.feed.data.entity.FeedEntity

@Database(entities = [FeedEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
}
