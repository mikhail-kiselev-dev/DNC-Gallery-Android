package com.dnc.androidgallery.core.extensions

import android.app.Service
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("unused")
fun <T> MutableLiveData<T>.callAndNullify(message: T) {
    value = message
    value = null
}

fun <T> Fragment.subscribe(flow: (Flow<T?>)?, onNext: (t: T) -> Unit) {
    flow ?: return
    viewLifecycleOwner.lifecycle.coroutineScope.launchWhenResumed {
        flow.collect {
            if (it != null) {
                onNext(it)
            }
        }
    }
}

fun <T> AppCompatActivity.subscribe(flow: (Flow<T?>)?, onNext: (t: T) -> Unit) {
    flow ?: return
    lifecycle.coroutineScope.launch {
        flow.collect {
            if (it != null) {
                onNext(it)
            }
        }
    }
}

fun <T> AppCompatActivity.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(this, {
        if (it != null) {
            onNext(it)
        }
    })
}

fun <T> Fragment.subscribeNullable(liveData: LiveData<T>?, onNext: (t: T?) -> Unit) {
    liveData ?: return
    liveData.observe(viewLifecycleOwner, Observer { onNext(it) })
}

fun FragmentActivity.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    globalToast(this, text, duration)

fun FragmentActivity.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    globalToast(this, getString(resId), duration)

fun Fragment.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    requireActivity().showToast(text, duration)

@Suppress("unused")
fun Fragment.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) =
    requireActivity().showToast(resId, duration)

@Suppress("unused")
fun Service.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    globalToast(this, text, duration)

private fun globalToast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, text, duration).show()

fun <T> Fragment.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(viewLifecycleOwner, {
        if (it != null) {
            onNext(it)
        }
    })
}

@Suppress("unused")
fun <T> LiveData<T>.mutable(): MutableLiveData<T> =
    (this as? MutableLiveData) ?: throw IllegalArgumentException("LiveData is not mutable")

@Suppress("unused")
fun <T> StateFlow<T>.mutable(): MutableStateFlow<T> =
    (this as? MutableStateFlow) ?: throw IllegalArgumentException("StateFlow is not mutable")
