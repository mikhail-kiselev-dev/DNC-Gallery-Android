package com.dnc.androidgallery.features.auth.di

import com.dnc.androidgallery.features.auth.ui.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { Firebase.auth }
    viewModel { AuthViewModel(get(), get()) }
}
