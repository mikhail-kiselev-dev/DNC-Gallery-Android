package com.dnc.androidgallery.features.details.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.core.data.FeedType
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentDetailsHolderBinding
import com.dnc.androidgallery.features.details.ui.list.DetailsScreenSlidePagerAdapter
import com.dnc.androidgallery.features.feed.ui.list.InvokeOnPageChangeListener
import kotlinx.android.synthetic.main.fragment_details_holder.*
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf

class DetailsHolderFragment : BaseFragment<DetailsHolderViewModel, FragmentDetailsHolderBinding>(
    R.layout.fragment_details_holder,
    FragmentDetailsHolderBinding::bind
) {

    private val args by navArgs<DetailsHolderFragmentArgs>()
    override fun getParameters(): ParametersDefinition =
        { parametersOf(args.detailsInfo) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPhotosTotal()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.photosCount) {
            setupPager(it)
        }
    }

    private fun setupPager(photosTotal: Int) {
        val currentIndex = ((args.detailsInfo.page - 1) * 9) + args.detailsInfo.position
        tvPage.text = getString(R.string.page_num, currentIndex.toString(), photosTotal.toString())
        tvPage.visibility = View.VISIBLE
        val listener = InvokeOnPageChangeListener { position ->
            tvPage.text = getString(
                R.string.page_num,
                (position + 1).toString(),
                viewModel.photosCount.value.toString()
            )
        }
        vpDetails.addOnPageChangeListener(listener)
        vpDetails.adapter = DetailsScreenSlidePagerAdapter(
            requireActivity().supportFragmentManager,
            photosTotal,
            args.detailsInfo
        )
        vpDetails.currentItem = currentIndex - 1
        if (args.detailsInfo.content == FeedType.RECENT) {
            tvPage.visibility = View.GONE
            vpDetails.disableScroll(true)
        }
    }
}
