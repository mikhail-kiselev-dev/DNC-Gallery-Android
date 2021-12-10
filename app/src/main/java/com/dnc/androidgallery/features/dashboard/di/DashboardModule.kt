package com.dnc.androidgallery.features.dashboard.di

import com.dnc.androidgallery.features.dashboard.ui.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
    viewModel { DashboardViewModel() }
}
