package com.dnc.androidgallery.features.feed.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dnc.androidgallery.features.feed.ui.BaseFeedFragment

class ScreenSlidePagerAdapter(fm: FragmentManager, private val pages: Int) :
    FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return pages
    }

    override fun getItem(position: Int): Fragment {
        return BaseFeedFragment(position)
    }
}
