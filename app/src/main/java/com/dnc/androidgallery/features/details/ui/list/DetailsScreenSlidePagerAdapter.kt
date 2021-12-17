package com.dnc.androidgallery.features.details.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dnc.androidgallery.features.details.ui.DetailsFragment
import com.dnc.androidgallery.features.details.ui.model.DetailsItemInfo

class DetailsScreenSlidePagerAdapter(fm: FragmentManager, private val photosTotal: Int, private val info: DetailsItemInfo) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return photosTotal
    }

    override fun getItem(position: Int): Fragment {
        return DetailsFragment(info, position)
    }
}
