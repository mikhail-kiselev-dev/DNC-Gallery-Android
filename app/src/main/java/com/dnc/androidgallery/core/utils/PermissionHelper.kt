package com.dnc.androidgallery.core.utils

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dnc.androidgallery.core.extensions.showToast

@Suppress("unused")
object PermissionHelper {
    fun permissionChecker(fragment: Fragment, declineMessage: String, proceed: (Boolean) -> Unit, permission: String) {
        val requestPermissionLauncher =
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    proceed.invoke(true)
                } else {
                    fragment.showToast(declineMessage)
                    proceed.invoke(false)
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                proceed.invoke(true)
            }
            fragment.shouldShowRequestPermissionRationale(
                permission
            ) -> {
                fragment.showToast(declineMessage)
                requestPermissionLauncher.launch(
                    permission
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    permission
                )
            }
        }
    }
}
