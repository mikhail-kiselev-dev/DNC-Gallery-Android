package com.dnc.androidgallery.features.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.base.recycler.ItemModel
import com.dnc.androidgallery.core.data.Image
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.features.settings.ui.model.SettingItemModel
import com.dnc.androidgallery.features.settings.ui.model.Settings

class SettingsViewModel : BaseViewModel() {
    val settings: LiveData<List<ItemModel>> = MutableLiveData()

    init {
        val settingsList = listOf(
            SettingItemModel(
                title = "Sign out",
                image = Image.ImgRes(R.drawable.ic_log_out),
                Settings.LOGOUT
            )
        )
        settings.mutable().value = settingsList
    }
}
