package com.dnc.androidgallery.core.extensions

import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

@Suppress("unused")
fun View.doOnApplyWindowInsets(block: (View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat) {
    doOnApplyWindowInsets(this, block)
}

fun View.doOnApplyWindowInsets(
    targetView: View,
    block: (View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat
) {
    val initialPadding = recordInitialPaddingForView(targetView)
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        block(targetView, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

val TextInputLayout.data: String?
    get() = this.editText?.text?.toString()

fun TextView.hideIfEmpty(content: String?) {
    if (content.isNullOrBlank()) {
        this.visibility = View.GONE
    } else {
        this.text = content.replace("\n", " ").trim()
        this.visibility = View.VISIBLE
    }
}
