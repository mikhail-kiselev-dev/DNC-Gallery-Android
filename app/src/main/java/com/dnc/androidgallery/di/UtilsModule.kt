package com.dnc.androidgallery.di

import com.dnc.androidgallery.base.EmptyViewModel
import com.dnc.androidgallery.core.network.ConnectionStatusListener
import com.dnc.androidgallery.core.network.ConnectionStatusListenerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val utilsModule = module {
    single<ConnectionStatusListener> { ConnectionStatusListenerImpl(androidContext()) }
    viewModel { EmptyViewModel() }
}
