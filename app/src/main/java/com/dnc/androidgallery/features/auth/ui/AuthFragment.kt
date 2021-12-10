package com.dnc.androidgallery.features.auth.ui

import android.os.Bundle
import android.view.View
import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseFragment
import com.dnc.androidgallery.core.extensions.clearErrorOnChange
import com.dnc.androidgallery.core.extensions.data
import com.dnc.androidgallery.core.extensions.subscribe
import com.dnc.androidgallery.core.extensions.subscribeNullable
import com.dnc.androidgallery.databinding.FragmentAuthBinding
import com.dnc.androidgallery.features.auth.ui.model.User
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding>(
    R.layout.fragment_auth,
    FragmentAuthBinding::bind
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        tilEmail.clearErrorOnChange()
        tilPassword.clearErrorOnChange()
        goButton.setOnClickListener {
            if (validateCredentials()) {
                viewModel.login(
                    // Checks on null in VM
                    User(
                        username = tilEmail.data!!,
                        password = tilPassword.data!!
                    )
                )
            }
        }
    }

    private fun validateCredentials(): Boolean {
        val email = tilEmail.data
        val password = tilPassword.data
        return viewModel.validateFields(email, password)
    }

    override fun observeLiveData() {
        super.observeLiveData()
        subscribe(viewModel.emailError) {
            tilEmail.error = it
        }
        subscribe(viewModel.passwordError) {
            tilPassword.error = it
        }
        subscribeNullable(viewModel.navigateNext) {
            baseActivity.goToMainApp()
        }
    }
}
