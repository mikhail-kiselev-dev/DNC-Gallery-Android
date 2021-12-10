package com.dnc.androidgallery.features.settings.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>(
    R.layout.fragment_settings,
    FragmentSettingsBinding::bind
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            mainViewModel.initStartDestinationBy(R.id.authFragment)
        }
    }
}
