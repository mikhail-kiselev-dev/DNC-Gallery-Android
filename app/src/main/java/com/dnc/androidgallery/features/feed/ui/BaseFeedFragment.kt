package com.dnc.androidgallery.features.feed.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.base.recycler.RecyclerDelegationAdapter
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.core.utils.logd
import com.dnc.androidgallery.databinding.FragmentFeedBinding
import com.dnc.androidgallery.features.feed.ui.list.FeedAdapterDelegate
import kotlinx.android.synthetic.main.fragment_feed.*

class BaseFeedFragment(private val position: Int) : BaseFragment<BaseFeedViewModel, FragmentFeedBinding>(
    R.layout.fragment_feed,
    FragmentFeedBinding::bind
) {

    private val adapter by lazy {
        RecyclerDelegationAdapter(requireContext()).apply {
            addDelegate(
                FeedAdapterDelegate(
                    requireContext(),
                    clickListener = {
                        logd(getString(R.string.under_construction))
                    }
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoadingState()
        binding.apply {
            rvFeed.adapter = adapter
        }
        viewModel.loadFeed(position + 1)
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.topFeed) {
            setReadyState()
            adapter.setItems(it)
        }
    }

    private fun setLoadingState() {
        rvFeed.visibility = View.INVISIBLE
        loaderFeed.visibility = View.VISIBLE
    }

    private fun setReadyState() {
        rvFeed.visibility = View.VISIBLE
        loaderFeed.visibility = View.INVISIBLE
    }
}
