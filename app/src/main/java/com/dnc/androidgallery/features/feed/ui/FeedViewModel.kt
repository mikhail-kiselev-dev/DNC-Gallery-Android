package com.dnc.androidgallery.features.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.features.feed.domain.FeedInteractor
import com.dnc.androidgallery.features.feed.ui.model.FeedItemModel
import com.dnc.androidgallery.features.feed.ui.model.itemModel
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

open class FeedViewModel(
    private val interactor: FeedInteractor,
) : BaseViewModel() {

    val currentFeed: LiveData<List<FeedItemModel>> = MutableLiveData()

    fun loadFeed(content: FeedType, page: Int, dateUnix: Long? = null) {
        val date = if (dateUnix != null) {
            SimpleDateFormat("yyyy-MM-dd").format(dateUnix)
        } else {
            null
        }
        launch(dispatcher = ioContext) {
            val feed = when (content) {
                FeedType.TOP -> {
                    interactor.getTopPhotos(page, date).map {
                        it.itemModel()
                    }
                }
                FeedType.RECENT -> {
                    interactor.getRecentPhotos(page).map {
                        it.itemModel()
                    }
                }
            }
            withContext(mainContext) {
                currentFeed.mutable().value = feed
            }
        }
    }
}
