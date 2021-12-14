package com.dnc.androidgallery.features.feed.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentTopFeedBinding
import com.dnc.androidgallery.features.feed.ui.list.InvokeOnPageChangeListener
import com.dnc.androidgallery.features.feed.ui.list.ScreenSlidePagerAdapter
import kotlinx.android.synthetic.main.fragment_top_feed.*

class TopFeedFragment : BaseFragment<TopFeedViewModel, FragmentTopFeedBinding>(
    R.layout.fragment_top_feed,
    FragmentTopFeedBinding::bind
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPagesTotal()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.pagesCount) {
            setupPager(it)
        }
    }

    private fun setupPager(pagesTotal: Int) {
        val firstPage = "1"
        tvPage.text = getString(R.string.page_num, firstPage, pagesTotal.toString())
        tvPage.visibility = View.VISIBLE
        vpTopFeed.addOnPageChangeListener(
            InvokeOnPageChangeListener { position ->
                tvPage.text = getString(
                    R.string.page_num,
                    (position + 1).toString(),
                    viewModel.pagesCount.value.toString()
                )
            }
        )
        vpTopFeed.adapter = ScreenSlidePagerAdapter(
            requireActivity().supportFragmentManager,
            pagesTotal
        )
    }
}
