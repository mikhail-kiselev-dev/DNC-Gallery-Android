package com.dnc.androidgallery.features.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.features.feed.domain.FeedInfoInteractor
import kotlinx.coroutines.withContext

class FeedHolderViewModel(
    private val interactor: FeedInfoInteractor
) : BaseViewModel() {

    val pagesCount: LiveData<Int> = MutableLiveData()

    fun loadPagesTotal(content: FeedType) {
        launch(dispatcher = ioContext) {
            val pagesTotal = when (content) {
                FeedType.TOP -> {
                    interactor.getTotalPagesTop()
                }
                FeedType.RECENT -> {
                    interactor.getTotalPagesRecent()
                }
            }
            withContext(mainContext) {
                pagesCount.mutable().value = pagesTotal
            }
        }
    }
}
