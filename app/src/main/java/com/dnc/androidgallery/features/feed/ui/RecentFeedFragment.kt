package com.dnc.androidgallery.features.feed.ui

import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.databinding.FragmentRecentFeedBinding

class RecentFeedFragment : BaseFragment<RecentFeedViewModel, FragmentRecentFeedBinding>(
    R.layout.fragment_recent_feed,
    FragmentRecentFeedBinding::bind
)
