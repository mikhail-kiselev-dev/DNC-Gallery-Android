package com.dnc.androidgallery.features.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.features.details.domain.PhotoDetailsInteractor
import com.dnc.androidgallery.features.details.ui.model.DetailsItem
import com.dnc.androidgallery.features.details.ui.model.DetailsItemInfo
import com.dnc.androidgallery.features.details.ui.model.toItem
import com.dnc.androidgallery.features.feed.domain.FeedInfoInteractor
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val interactor: PhotoDetailsInteractor,
    private val feedInfoInteractor: FeedInfoInteractor
) : BaseViewModel() {
    val photoDetails: LiveData<DetailsItem> = MutableLiveData()

    fun getPhotoDetails(info: DetailsItemInfo, pos: Int) {
        launch(dispatcher = ioContext) {
            val id = when (info.content) {
                FeedType.TOP -> {
                    getPhotoId(info.content, pos)
                }
                FeedType.RECENT -> {
                    info.id
                }
            }
            val details = interactor.getInfo(id)
            withContext(mainContext) {
                photoDetails.mutable().value = details.toItem()
            }
        }
    }

    private suspend fun getPhotoId(content: FeedType, position: Int): Long {
        return feedInfoInteractor.getPhotoId(content, position)
    }
}
