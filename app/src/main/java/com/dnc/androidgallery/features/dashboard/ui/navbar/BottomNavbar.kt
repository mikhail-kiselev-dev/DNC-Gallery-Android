package com.dnc.androidgallery.features.dashboard.ui.navbar

interface BottomNavbar {
    var currentActiveTab: BottomItem
    var prevActiveTab: BottomItem
    var destinationListener: ((activeBottomItem: BottomItem, commitNow: Boolean) -> Unit)?
    fun setActiveTab(activeBottomItem: BottomItem)
}
