package com.dnc.androidgallery.features.settings.di

import com.dnc.androidgallery.features.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel() }
}
