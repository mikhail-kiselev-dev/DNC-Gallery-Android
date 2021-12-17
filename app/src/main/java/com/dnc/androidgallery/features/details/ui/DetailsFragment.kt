package com.dnc.androidgallery.features.details.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.core.data.Image
import com.dnc.androidgallery.core.extensions.hideIfEmpty
import com.dnc.androidgallery.core.extensions.loadImage
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentDetailsBinding
import com.dnc.androidgallery.features.details.ui.model.DetailsItemInfo
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment(private val info: DetailsItemInfo, private val position: Int) : BaseFragment<DetailsViewModel, FragmentDetailsBinding>(
    R.layout.fragment_details,
    FragmentDetailsBinding::bind
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoadingState()
        viewModel.getPhotoDetails(info, position + 1)
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.photoDetails) {
            setReadyState()
            ivPhoto.loadImage(Image.ImgUrl(it.url))
            tvTitle.hideIfEmpty(it.title)
            tvDescription.hideIfEmpty(it.description)
            tvUsername.hideIfEmpty(it.author)
            tvLocation.hideIfEmpty(it.location)
            tvTaken.hideIfEmpty(it.taken)
            tvViews.hideIfEmpty(it.views)
        }
    }

    private fun setLoadingState() {
        hideViews(
            ivPhoto, tvTitle, tvDescription, tvUsername, tvLocation, tvTaken, tvViews
        )
        detailsLoader.visibility = View.VISIBLE
    }

    private fun setReadyState() {
        showViews(
            ivPhoto, tvTitle, tvDescription, tvUsername, tvLocation, tvTaken, tvViews
        )
        detailsLoader.visibility = View.INVISIBLE
    }

    private fun hideViews(vararg views: View) {
        views.forEach {
            it.visibility = View.INVISIBLE
        }
    }

    private fun showViews(vararg views: View) {
        views.forEach {
            it.visibility = View.VISIBLE
        }
    }
}
