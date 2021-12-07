package com.dnc.androidgallery.features.feed.di

import com.dnc.androidgallery.features.feed.ui.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    viewModel { FeedViewModel() }
}
