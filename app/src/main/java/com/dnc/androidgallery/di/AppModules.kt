package com.dnc.androidgallery.di

import com.dnc.androidgallery.features.auth.di.authModule
import com.dnc.androidgallery.features.dashboard.di.dashboardModule
import com.dnc.androidgallery.features.feed.di.feedModule
import com.dnc.androidgallery.features.settings.di.settingsModule

val applicationModules = listOf(
    providersModule,
    coreModule,
    utilsModule,
    apiModule,
    authModule,
    feedModule,
    dashboardModule,
    settingsModule,
)
