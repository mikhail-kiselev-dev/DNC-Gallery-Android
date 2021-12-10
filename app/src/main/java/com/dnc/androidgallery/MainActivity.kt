package com.dnc.androidgallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.dnc.androidgallery.base.navigation.NavigationCommandHandler
import com.dnc.androidgallery.core.extensions.showToast
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.core.network.ConnectionStatusListener
import com.dnc.androidgallery.databinding.ActivityMainBinding
import com.dnc.androidgallery.features.splash.ui.SplashFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private val navigationCommandHandler: NavigationCommandHandler by lazy {
        NavigationCommandHandler(
            navControllerDefinition = { binding.navHost.findNavController() }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initFragment()
        subscribeConnectionListener()
    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.navHost)
        if (fragment != null) {
            onNavGraphInited()
            return
        } else {
            val startDestinationId = if (mainViewModel.isLoggedIn()) {
                R.id.dashboardFragment
            } else {
                R.id.authFragment
            }
            mainViewModel.initStartDestinationBy(startDestinationId)
        }

        subscribe(mainViewModel.startDestinationInitialized) { destinationWithBundle ->
            initNavigationGraph(destinationWithBundle.first, destinationWithBundle.second)
        }
    }

    @Suppress("unused")
    fun goToMainApp() {
        mainViewModel.initStartDestinationBy(R.id.dashboardFragment)
    }

    private fun onNavGraphInited() {
        lifecycle.coroutineScope.launch {
            val fragment = supportFragmentManager.findFragmentById(R.id.navHost)

            fragment?.lifecycle?.whenStarted {
                fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
                    mainViewModel.currentDestination.value = destination
                }

                fragment.view?.post {
                    startSplashAnimation()
                }
            }
        }
        subscribe(mainViewModel.navigationCommand) {
            navigationCommandHandler.handle(this, it)
        }
    }

    private fun subscribeConnectionListener() {
        subscribe(mainViewModel.connectionListenerFlow) {
            updateNoConnectionMessage(it)
        }
    }

    private fun initNavigationGraph(startDestinationId: Int, startDestinationBundle: Bundle?) {
        val navHostFragment = NavHostFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navHost, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()

        val graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.navigation_main)
                .apply {
                    startDestination = startDestinationId
                }

        navHostFragment.navController.setGraph(graph, startDestinationBundle)
        onNavGraphInited()
    }

    private fun startSplashAnimation() {
        val splashFragment =
            supportFragmentManager.findFragmentById(R.id.splashHost) as? SplashFragment
        splashFragment?.startSplashAnimation {
            lifecycleScope.launchWhenResumed {
                if (splashFragment.isAdded) {
                    supportFragmentManager.beginTransaction().remove(splashFragment).commitNow()
                    binding.frameContainer.removeView(binding.splashHost)
                }
            }
        }
    }

    private fun updateNoConnectionMessage(status: ConnectionStatusListener.ConnectionStatusState) {
        when (status) {
            ConnectionStatusListener.ConnectionStatusState.Default,
            ConnectionStatusListener.ConnectionStatusState.HasConnection,
            ConnectionStatusListener.ConnectionStatusState.Dismissed -> {
                showToast("Network connected")
            }

            ConnectionStatusListener.ConnectionStatusState.NoConnection -> {
                showToast("Network disconnected")
            }
        }
    }
}
