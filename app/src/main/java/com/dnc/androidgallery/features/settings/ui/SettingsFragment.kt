package com.dnc.androidgallery.features.settings.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.base.recycler.RecyclerDelegationAdapter
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.databinding.FragmentSettingsBinding
import com.dnc.androidgallery.features.settings.ui.list.SettingsAdapterDelegate
import com.dnc.androidgallery.features.settings.ui.model.Settings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.view_title.view.*

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>(
    R.layout.fragment_settings,
    FragmentSettingsBinding::bind
) {

    private val adapter by lazy {
        RecyclerDelegationAdapter(requireContext()).apply {
            addDelegate(
                SettingsAdapterDelegate(
                    requireContext(),
                    clickListener = {
                        when (it.setting) {
                            Settings.LOGOUT -> {
                                FirebaseAuth.getInstance().signOut()
                                mainViewModel.initStartDestinationBy(R.id.authFragment)
                            }
                        }
                    }
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitleBar()
        binding.apply {
            rvSettings.adapter = adapter
        }
    }

    private fun setupTitleBar() {
        val bottomNavbar = parentFragment?.parentFragment?.bottomNavbar ?: return
        binding.titleView.ivBack.setOnClickListener {
            bottomNavbar.setActiveTab(
                bottomNavbar.prevActiveTab
            )
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.settings) {
            adapter.setItems(it)
        }
    }
}
