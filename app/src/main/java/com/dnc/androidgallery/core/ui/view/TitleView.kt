package com.dnc.androidgallery.core.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dnc.androidgallery.R
import com.dnc.androidgallery.databinding.ViewTitleBinding
import java.lang.RuntimeException

class TitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {
    private val binding: ViewTitleBinding =
        ViewTitleBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        val attributeArray = getAttrsArray(attrs)
        binding.tvTitle.text = attributeArray.getString(R.styleable.TitleView_title) ?: throw RuntimeException("Title required for TitleView")
    }

    private fun getAttrsArray(attrs: AttributeSet?): TypedArray {
        return context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TitleView,
            0,
            0
        )
    }
}
