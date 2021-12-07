package com.dnc.androidgallery.core.extensions

import androidx.core.text.HtmlCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

fun String.Companion.htmlFormat(format: String, vararg args: Any?): CharSequence {
    return HtmlCompat.fromHtml(
        if (args.isEmpty()) format else String.format(format, *args),
        HtmlCompat.FROM_HTML_MODE_COMPACT
    )
}

@Suppress("unused")
fun TextInputLayout.clearErrorOnChange() {
    editText?.addTextChangedListener {
        error = null
    }
}
