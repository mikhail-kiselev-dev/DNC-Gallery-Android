package com.dnc.androidgallery.features.dashboard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.extensions.mutable
import com.dnc.androidgallery.core.utils.SingleLiveEvent
import com.dnc.androidgallery.features.dashboard.ui.navbar.BottomItem
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine

class DashboardViewModel : BaseViewModel() {

    lateinit var currentBottomNavController: LiveData<NavController>

    lateinit var isStartDestination: LiveData<Boolean>

    val bottomBarVisible: LiveData<Boolean> = MutableLiveData(true)

    val selectedTab = SingleLiveEvent<BottomItem>()

    init {
        launch {
            currentDestination.collect {
                bottomBarVisible.mutable().value =
                    when (it?.id) {
                        R.id.settingsFragment -> false
                        else -> true
                    }
            }
        }
    }

    fun connectBottomNavController(navControllerFlow: StateFlow<NavController>) {
        currentBottomNavController = navControllerFlow.asLiveData()
        isStartDestination =
            combine(currentDestination, navControllerFlow) { navDestination, navController ->
                navController.graph.startDestination == navDestination?.id
            }.asLiveData()
    }
}
