package com.dnc.androidgallery.features.feed.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentFeedHolderBinding
import com.dnc.androidgallery.features.feed.ui.list.InvokeOnPageChangeListener
import com.dnc.androidgallery.features.feed.ui.list.ScreenSlidePagerAdapter
import kotlinx.android.synthetic.main.fragment_feed_holder.*
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf

class FeedHolderFragment : BaseFragment<FeedHolderViewModel, FragmentFeedHolderBinding>(
    R.layout.fragment_feed_holder,
    FragmentFeedHolderBinding::bind
) {

    private val args by navArgs<FeedHolderFragmentArgs>()
    override fun getParameters(): ParametersDefinition =
        { parametersOf(args.feedType) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPagesTotal(args.feedType)
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
        vpFeed.addOnPageChangeListener(
            InvokeOnPageChangeListener { position ->
                tvPage.text = getString(
                    R.string.page_num,
                    (position + 1).toString(),
                    viewModel.pagesCount.value.toString()
                )
            }
        )
        vpFeed.adapter = ScreenSlidePagerAdapter(
            requireActivity().supportFragmentManager,
            pagesTotal,
            args.feedType
        )
    }
}
