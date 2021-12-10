package com.dnc.androidgallery.di

import com.dnc.androidgallery.MainViewModel
import com.dnc.androidgallery.core.utils.ExceptionParser
import com.dnc.androidgallery.di.scope.AUTHORIZED_SCOPE_NAME
import com.dnc.androidgallery.di.scope.EMPTY_SCOPE_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    scope(EMPTY_SCOPE_NAME) {
    }

    scope(AUTHORIZED_SCOPE_NAME) {
    }

    single { ExceptionParser(get()) }
    viewModel { MainViewModel(get(), get()) }
}
