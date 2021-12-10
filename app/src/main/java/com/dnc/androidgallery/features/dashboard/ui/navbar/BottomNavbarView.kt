package com.dnc.androidgallery.features.dashboard.ui.navbar

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dnc.androidgallery.R
import com.dnc.androidgallery.databinding.ViewBottomNavbarBinding
import kotlinx.android.synthetic.main.view_bottom_navbar.view.*

class BottomNavbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : BottomNavbar, ConstraintLayout(context, attrs) {
    private val binding: ViewBottomNavbarBinding =
        ViewBottomNavbarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private val viewsList = listOf(
        binding.ivTop to binding.tvTop,
        binding.ivRecent to binding.tvRecent,
        binding.ivSettings to binding.tvSettings
    )

    override var currentActiveTab = BottomItem.TOP

    override var destinationListener: ((activeBottomItem: BottomItem, commitNow: Boolean) -> Unit)? =
        null

    override var prevActiveTab = BottomItem.TOP

    init {
        setActiveTabsColor()
        setActiveTab(currentActiveTab)
        setListeners()
    }

    private fun setActiveTabsColor() {
        BottomItem.TOP.colorTitleActive = R.color.secondary
        BottomItem.TOP.iconResActive = R.drawable.ic_top_selected
        BottomItem.RECENT.colorTitleActive = R.color.secondary
        BottomItem.RECENT.iconResActive = R.drawable.ic_recent_selected
        BottomItem.SETTINGS.colorTitleActive = R.color.secondary
        BottomItem.SETTINGS.iconResActive = R.drawable.ic_settings_selected
    }

    private fun setListeners() {
        val topListener = OnClickListener {
            setActiveTab(BottomItem.TOP)
        }
        ivTop.setOnClickListener(topListener)
        tvTop.setOnClickListener(topListener)

        val recentListener = OnClickListener {
            setActiveTab(BottomItem.RECENT)
        }
        ivRecent.setOnClickListener(recentListener)
        tvRecent.setOnClickListener(recentListener)

        val settingsListener = OnClickListener {
            setActiveTab(BottomItem.SETTINGS)
        }
        ivSettings.setOnClickListener(settingsListener)
        tvSettings.setOnClickListener(settingsListener)
    }

    override fun setActiveTab(activeBottomItem: BottomItem) {
        destinationListener?.invoke(activeBottomItem, true)
        prevActiveTab = currentActiveTab
        currentActiveTab = activeBottomItem
        viewsList.forEach {
            if (it.first.id == activeBottomItem.viewId) {
                it.first.setImageResource(activeBottomItem.iconResActive)
                it.second.setTextColor(context.getColor(activeBottomItem.colorTitleActive))
            } else {
                it.first.setImageResource(BottomItem.findByViewID(it.first.id).iconResDefault)
                it.second.setTextColor(context.getColor(BottomItem.findByViewID(it.first.id).colorTitleDefault))
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putInt(SELECTED_TAB_STATE, currentActiveTab.viewId)
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val activeTab = state.getInt(SELECTED_TAB_STATE, R.id.ivTop)
            setActiveTab(BottomItem.findByViewID(activeTab))
            super.onRestoreInstanceState(state.getParcelable(SUPER_STATE))
        }
    }

    companion object {
        private const val SELECTED_TAB_STATE = "selectedTab"
        private const val SUPER_STATE = "superState"
    }
}
