package com.dnc.androidgallery.core.extensions

import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ViewModelOwner

fun Fragment.requestNotHostParentFragment(): Fragment? {
    var parent = parentFragment
    while (parent is NavHostFragment) {
        parent = parent.parentFragment
    }
    return parent
}

fun Fragment.getParentFragmentByClass(classType: Class<out Fragment>): Fragment {
    var parent = requireParentFragment()
    while (parent::class.java != classType) {
        parent = parent.requireParentFragment()
    }
    return parent
}

fun Fragment.asOwnerDefinition(): () -> ViewModelOwner = {
    ViewModelOwner.from(this, this)
}

fun Fragment.getParentFragment(ignoreNavHostFragment: Boolean): Fragment? =
    parentFragment?.let { fragment ->
        if (fragment is NavHostFragment && ignoreNavHostFragment) {
            fragment.getParentFragment(true)
        } else {
            fragment
        }
    }

fun NavHostFragment.getCurrentFragment(): Fragment? = childFragmentManager.primaryNavigationFragment

inline fun Fragment.onBackPressedCallback(crossinline onBackPressedCallback: OnBackPressedCallback.() -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        onBackPressedCallback()
    }
}
