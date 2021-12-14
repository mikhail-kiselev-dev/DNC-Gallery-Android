package com.dnc.androidgallery.features.feed.ui.list

import androidx.viewpager.widget.ViewPager

class InvokeOnPageChangeListener(
    private val callback: (Int) -> Unit
) : ViewPager.OnPageChangeListener {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        callback.invoke(position)
    }

    override fun onPageScrollStateChanged(state: Int) {}
}
