package com.dnc.androidgallery.features.feed.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.databinding.FragmentFeedBinding

class FeedFragment : BaseFragment<FeedViewModel, FragmentFeedBinding>(
    R.layout.fragment_feed,
    FragmentFeedBinding::bind
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
