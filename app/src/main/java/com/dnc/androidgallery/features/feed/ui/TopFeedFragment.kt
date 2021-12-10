package com.dnc.androidgallery.features.feed.ui

import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.databinding.FragmentTopFeedBinding

class TopFeedFragment : BaseFragment<TopFeedViewModel, FragmentTopFeedBinding>(
    R.layout.fragment_top_feed,
    FragmentTopFeedBinding::bind
)
