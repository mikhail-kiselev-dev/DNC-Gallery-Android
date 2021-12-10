package com.dnc.androidgallery.features.auth.ui

import com.dnc.androidgallery.R
import com.dnc.androidgallery.base.BaseViewModel
import com.dnc.androidgallery.core.providers.ResourceProvider
import com.dnc.androidgallery.core.utils.SingleLiveEvent
import com.dnc.androidgallery.core.utils.loge
import com.dnc.androidgallery.features.auth.ui.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val emailError = SingleLiveEvent<String?>()
    val passwordError = SingleLiveEvent<String?>()
    val navigateNext = SingleLiveEvent<Unit?>()

    fun validateFields(email: String?, password: String?): Boolean {
        return when {
            email.isNullOrEmpty() -> {
                emailError.value = resourceProvider.getString(R.string.email_empty)
                false
            }
            !email.contains("@") -> {
                emailError.value = resourceProvider.getString(R.string.not_an_email)
                false
            }
            password.isNullOrEmpty() -> {
                passwordError.value = resourceProvider.getString(R.string.password_empty)
                false
            }
            password.length < 6 -> {
                passwordError.value = resourceProvider.getString(R.string.password_to_short)
                false
            }
            else -> true
        }
    }

    fun login(user: User) {
        firebaseAuth.signInWithEmailAndPassword(user.username, user.password)
            .addOnCompleteListener {
                navigateOnSuccess(it, user)
            }
    }

    private fun signUp(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(user.username, user.password)
            .addOnCompleteListener {
                navigateOnSuccess(it)
            }
    }

    private fun navigateOnSuccess(task: Task<AuthResult>, user: User? = null) {
        when {
            task.isSuccessful -> {
                navigateNext()
            }
            task.exception is com.google.firebase.auth.FirebaseAuthInvalidUserException -> {
                signUp(user ?: return)
            }
            else -> {
                loge(task.exception.toString())
                // TODO Sentry capture
            }
        }
    }

    private fun navigateNext() {
        navigateNext.call()
    }
}
