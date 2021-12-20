package com.dnc.androidgallery.features.feed.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.features.feed.ui.FeedFragment

class ScreenSlidePagerAdapter(
    fm: FragmentManager,
    private val pages: Int,
    private val content: FeedType,
    private val callback: (Long, Int, Int) -> Unit
) :
    FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return pages
    }

    override fun getItem(position: Int): Fragment {
        return FeedFragment(position, content) { id, positionRecycler ->
            callback.invoke(id, positionRecycler, position)
        }
    }
}
