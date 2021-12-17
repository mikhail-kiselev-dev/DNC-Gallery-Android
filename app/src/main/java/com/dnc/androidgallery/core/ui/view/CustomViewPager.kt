package com.dnc.androidgallery.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(
    context: Context,
    attrs: AttributeSet?
) : ViewPager(context, attrs) {

    private var disable: Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !disable && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return !disable && super.onTouchEvent(ev)
    }

    fun disableScroll(disable: Boolean) {
        this.disable = disable
    }
}
