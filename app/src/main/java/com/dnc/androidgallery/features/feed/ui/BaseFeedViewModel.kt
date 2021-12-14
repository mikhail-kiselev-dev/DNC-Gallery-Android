package com.dnc.androidgallery.features.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.features.feed.domain.TopFeedInteractor
import com.dnc.androidgallery.features.feed.ui.model.FeedItemModel
import com.dnc.androidgallery.features.feed.ui.model.itemModel
import kotlinx.coroutines.withContext

class BaseFeedViewModel(
    private val interactor: TopFeedInteractor
) : BaseViewModel() {

    val topFeed: LiveData<List<FeedItemModel>> = MutableLiveData()

    fun loadFeed(page: Int, date: String? = null) {
        launch(dispatcher = ioContext) {
            val feed = interactor.getTopPhotos(page, date).map {
                it.itemModel()
            }
            withContext(mainContext) {
                topFeed.mutable().value = feed
            }
        }
    }
}
