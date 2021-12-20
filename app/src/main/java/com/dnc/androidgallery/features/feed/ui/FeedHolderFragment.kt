package com.dnc.androidgallery.features.feed.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentFeedHolderBinding
import com.dnc.androidgallery.features.details.ui.model.DetailsItemInfo
import com.dnc.androidgallery.features.feed.ui.list.InvokeOnPageChangeListener
import com.dnc.androidgallery.features.feed.ui.list.ScreenSlidePagerAdapter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
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

    private var pages = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        viewModel.loadPagesTotal(args.feedType)
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.pagesCount) {
            pages = it
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
        setupAdapter()
    }

    private fun setupListeners() {
        ivCalendar.setOnClickListener {
            val dayMs = 1000 * 60 * 60 * 24
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setCalendarConstraints(
                        CalendarConstraints.Builder()
                            .setValidator(DateValidatorPointBackward.before(System.currentTimeMillis() - dayMs))
                            .build()
                    ).build()
            datePicker.show(requireActivity().supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                setupAdapter(it)
            }
        }
    }

    private fun setupAdapter(date: Long? = null) {
        vpFeed.adapter = ScreenSlidePagerAdapter(
            requireActivity().supportFragmentManager,
            pages,
            args.feedType,
            date
        ) { itemId, position, page ->
            viewModel.navigate(
                FeedHolderFragmentDirections.actionFeedHolderFragmentToDetailsHolderFragment(
                    DetailsItemInfo(
                        id = itemId,
                        page = page + 1,
                        position = position + 1,
                        content = args.feedType,
                    )
                )
            )
        }
    }
}
