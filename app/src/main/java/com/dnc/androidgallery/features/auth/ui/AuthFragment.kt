package com.dnc.androidgallery.features.auth.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(
    R.layout.fragment_auth,
    FragmentAuthBinding::bind
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
