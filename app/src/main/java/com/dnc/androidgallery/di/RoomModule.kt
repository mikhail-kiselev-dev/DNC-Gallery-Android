package com.dnc.androidgallery.di

import android.app.Application
import androidx.room.Room
import com.dnc.androidgallery.core.room.AppDatabase
import com.dnc.androidgallery.core.room.FeedDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "flickr")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AppDatabase): FeedDao {
        return dataBase.feedDao()
    }

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}
