package com.dnc.androidgallery.features.feed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.core.data.Image
import com.dnc.androidgallery.core.data.entity
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.core.room.FeedDao
import com.dnc.androidgallery.core.utils.SingleLiveEvent
import com.dnc.androidgallery.core.utils.loge
import com.dnc.androidgallery.features.feed.data.entity.FeedEntity
import com.dnc.androidgallery.features.feed.domain.FeedInteractor
import com.dnc.androidgallery.features.feed.ui.model.FeedItemModel
import com.dnc.androidgallery.features.feed.ui.model.itemModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val fakeDelay = 5000L

open class FeedViewModel(
    private val interactor: FeedInteractor,
    private val feedDao: FeedDao
) : BaseViewModel() {

    val currentFeed: LiveData<List<FeedItemModel>> = MutableLiveData()
    val fakeLoading = SingleLiveEvent<Unit?>()

    fun loadFeed(content: FeedType, page: Int, date: String? = null, isFirstInit: Boolean) {
        launch(dispatcher = ioContext) {
            if (isFirstInit) {
                val dbFeed = feedDao.getFeed()
                if (dbFeed.isNotEmpty()) {
                    withContext(mainContext) {
                        currentFeed.mutable().value = dbFeed.map {
                            FeedItemModel(
                                id = it.id,
                                title = it.title,
                                image = Image.ImgUrl(it.image)
                            )
                        }
                    }
                }
            }
            val feed = when (content) {
                FeedType.TOP -> {
                    interactor.getTopPhotos(page, date).map {
                        it.itemModel()
                    }
                }
                FeedType.RECENT -> {
                    interactor.getRecentPhotos(page, date).map {
                        it.itemModel()
                    }
                }
            }
            if (isFirstInit) {
                refreshDb(feed)
                withContext(mainContext) {
                    fakeLoading.call()
                }
                delay(fakeDelay)
            }
            withContext(mainContext) {
                currentFeed.mutable().value = feed
            }
        }
    }

    private suspend fun refreshDb(list: List<FeedItemModel>) {
        feedDao.clearDb()
        feedDao.insertAll(*list.map {
            FeedEntity(
                id = it.id,
                title = it.title,
                image = it.image.entity()
            )
        }.toTypedArray())
    }
}
