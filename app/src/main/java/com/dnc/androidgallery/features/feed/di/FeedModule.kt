package com.dnc.androidgallery.features.feed.di

import com.dnc.androidgallery.features.feed.data.datasource.*
import com.dnc.androidgallery.features.feed.domain.FeedInfoInteractor
import com.dnc.androidgallery.features.feed.domain.FeedInfoInteractorImpl
import com.dnc.androidgallery.features.feed.domain.FeedInteractor
import com.dnc.androidgallery.features.feed.domain.FeedInteractorImpl
import com.dnc.androidgallery.features.feed.ui.FeedHolderViewModel
import com.dnc.androidgallery.features.feed.ui.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    single<FeedDataSource> { FeedDataSourceImpl(get()) }
    factory<FeedInteractor> { FeedInteractorImpl(get()) }
    single<FeedInfoDataSource> { FeedInfoDataSourceImpl(get()) }
    factory<FeedInfoInteractor> { FeedInfoInteractorImpl(get()) }
    viewModel { FeedViewModel(get()) }
    viewModel { FeedHolderViewModel(get()) }
}
