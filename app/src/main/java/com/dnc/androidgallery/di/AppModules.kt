package com.dnc.androidgallery.di

import com.dnc.androidgallery.features.auth.di.authModule
import com.dnc.androidgallery.features.feed.di.feedModule

val applicationModules = listOf(
    providersModule,
    coreModule,
    utilsModule,
    apiModule,
    authModule,
    feedModule,
)
