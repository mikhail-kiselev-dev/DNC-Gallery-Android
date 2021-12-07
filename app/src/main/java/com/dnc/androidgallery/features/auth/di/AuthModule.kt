package com.dnc.androidgallery.features.auth.di

import com.dnc.androidgallery.features.auth.ui.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { AuthViewModel() }
}
