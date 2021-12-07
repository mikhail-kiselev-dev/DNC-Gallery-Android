package com.dnc.androidgallery

import android.os.Bundle
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.network.ConnectionStatusListener
import com.dnc.androidgallery.core.utils.SingleLiveEvent

class MainViewModel(
    connectionStatusListener: ConnectionStatusListener
) : BaseViewModel() {

    val startDestinationInitialized = SingleLiveEvent<Pair<Int, Bundle?>>()

    val connectionListenerFlow = connectionStatusListener.connectionStatusChannel

    fun initStartDestinationBy(destinationId: Int, bundle: Bundle? = null) {
        startDestinationInitialized.value = destinationId to bundle
    }

    fun isLoggedIn(): Boolean = false
}
