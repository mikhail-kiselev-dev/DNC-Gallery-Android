package com.dnc.androidgallery.features.dashboard.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseHostFragment
import com.dnc.androidgallery.base.BottomNavigationController
import com.dnc.androidgallery.core.extensions.getCurrentFragment
import com.dnc.androidgallery.core.extensions.onBackPressedCallback
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentDashboardBinding
import com.dnc.androidgallery.features.dashboard.ui.navbar.BottomItem
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseHostFragment<DashboardViewModel, FragmentDashboardBinding>(
    R.layout.fragment_dashboard,
    FragmentDashboardBinding::bind
) {

    private lateinit var bottomNavigationController: BottomNavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        initCallbacks()
    }

    private fun initCallbacks() {
        onBackPressedCallback {
            when (bottomNavbar.currentActiveTab) {
                BottomItem.SETTINGS -> {
                    bottomNavbar.setActiveTab(bottomNavbar.prevActiveTab)
                }
                else -> {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setupBottomNavController() {
        bottomNavigationController = BottomNavigationController(
            bottomGraphs = listOf(
                BottomNavigationController.BottomGraph(
                    BottomItem.TOP,
                    R.navigation.navigation_photos_top,
                    R.id.navigation_photos_top
                ),
                BottomNavigationController.BottomGraph(
                    BottomItem.RECENT,
                    R.navigation.navigation_photos_recent,
                    R.id.navigation_photos_recent
                ),
                BottomNavigationController.BottomGraph(
                    BottomItem.SETTINGS,
                    R.navigation.navigation_settings,
                    R.id.navigation_settings
                )
            ),
            fragmentManager = childFragmentManager,
            containerId = R.id.dashboardContainer
        )
    }

    private fun initBottomNavigation() {
        viewModel.connectBottomNavController(
            bottomNavigationController.setup(
                bottomNavigationView = bottomNavbar
            )
        )
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.bottomBarVisible) {
            binding.bottomNavbar.isVisible = it
        }
        subscribe(viewModel.selectedTab) {
            binding.bottomNavbar.setActiveTab(it)
        }
    }

    override fun getCurrentFragment(): Fragment? =
        (childFragmentManager.findFragmentById(R.id.dashboardContainer) as? NavHostFragment)?.getCurrentFragment()
}
