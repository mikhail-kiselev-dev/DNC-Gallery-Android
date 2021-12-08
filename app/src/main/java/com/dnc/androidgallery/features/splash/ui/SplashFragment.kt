package com.dnc.androidgallery.features.splash.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.dnc.androidgallery.R
import com.dnc.androidgallery.core.extensions.doOnApplyWindowInsets
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ANIM_DELAY = 2200L
private const val ANIM_DURATION = 800L

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var topInsetPixels = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnApplyWindowInsets { _, insets, _ ->
            insets.also {
                topInsetPixels = it.getInsets(WindowInsetsCompat.Type.ime()).top
            }
        }
    }

    fun startSplashAnimation(listener: () -> Unit) {
        container.doOnLayout {
            CoroutineScope(Dispatchers.Main).launch {
                delay(ANIM_DELAY)
                it.animate()
                    .alpha(0f)
                    .setDuration(ANIM_DURATION)
                    .setInterpolator(AccelerateInterpolator())
                    .withEndAction {
                        listener.invoke()
                    }
                    .start()
            }
        }
    }
}
