package com.dnc.androidgallery.features.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.features.feed.domain.FeedInfoInteractor
import kotlinx.coroutines.withContext

class DetailsHolderViewModel(
    private val interactor: FeedInfoInteractor,
) : BaseViewModel() {
    val photosCount: LiveData<Int> = MutableLiveData()

    fun loadPhotosTotal() {
        launch(dispatcher = ioContext) {
            val photosTotal = interactor.getTotalPhotosTop()
            withContext(mainContext) {
                photosCount.mutable().value = photosTotal
            }
        }
    }
}
