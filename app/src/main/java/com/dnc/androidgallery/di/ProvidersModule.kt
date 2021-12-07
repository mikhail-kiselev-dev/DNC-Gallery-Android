package com.dnc.androidgallery.di

import com.dnc.androidgallery.core.providers.PreferencesProvider
import com.dnc.androidgallery.core.providers.PreferencesProviderImpl
import com.dnc.androidgallery.core.providers.ResourceProvider
import com.dnc.androidgallery.core.providers.ResourceProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providersModule = module {
    factory<ResourceProvider> { ResourceProviderImpl(androidContext()) }
    factory<PreferencesProvider> { PreferencesProviderImpl(androidContext()) }
}
