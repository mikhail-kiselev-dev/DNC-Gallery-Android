package com.dnc.androidgallery.features.details.di

import com.dnc.androidgallery.features.details.data.datasource.PhotoDetailsDataSource
import com.dnc.androidgallery.features.details.data.datasource.PhotoDetailsDataSourceImpl
import com.dnc.androidgallery.features.details.domain.PhotoDetailsInteractor
import com.dnc.androidgallery.features.details.domain.PhotoDetailsInteractorImpl
import com.dnc.androidgallery.features.details.ui.DetailsHolderViewModel
import com.dnc.androidgallery.features.details.ui.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    single<PhotoDetailsDataSource> { PhotoDetailsDataSourceImpl(get()) }
    factory<PhotoDetailsInteractor> { PhotoDetailsInteractorImpl(get()) }
    viewModel { DetailsHolderViewModel(get()) }
    viewModel { DetailsViewModel(get(), get()) }
}
