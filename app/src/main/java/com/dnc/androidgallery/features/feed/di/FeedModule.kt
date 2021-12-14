package com.dnc.androidgallery.features.feed.di

import com.dnc.androidgallery.features.feed.data.datasource.TopFeedDataSource
import com.dnc.androidgallery.features.feed.data.datasource.TopFeedDataSourceImpl
import com.dnc.androidgallery.features.feed.domain.TopFeedInteractor
import com.dnc.androidgallery.features.feed.domain.TopFeedInteractorImpl
import com.dnc.androidgallery.features.feed.ui.BaseFeedViewModel
import com.dnc.androidgallery.features.feed.ui.RecentFeedViewModel
import com.dnc.androidgallery.features.feed.ui.TopFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    single<TopFeedDataSource> { TopFeedDataSourceImpl(get()) }
    factory<TopFeedInteractor> { TopFeedInteractorImpl(get()) }
    viewModel { BaseFeedViewModel(get()) }
    viewModel { TopFeedViewModel(get()) }
    viewModel { RecentFeedViewModel() }
}
