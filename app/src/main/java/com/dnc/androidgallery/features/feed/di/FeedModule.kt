package com.dnc.androidgallery.features.feed.di

import com.dnc.androidgallery.features.feed.ui.RecentFeedViewModel
import com.dnc.androidgallery.features.feed.ui.TopFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    viewModel { TopFeedViewModel() }
    viewModel { RecentFeedViewModel() }
}
