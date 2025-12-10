package com.randomjudge.util

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 扩展函数：为视图添加脉冲动画效果
 */
fun View.pulseAnimation() {
    val scaleAnimation = ScaleAnimation(
        1.0f, 1.1f, 1.0f, 1.1f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    )
    scaleAnimation.duration = 300
    scaleAnimation.repeatCount = 1
    scaleAnimation.repeatMode = Animation.REVERSE
    this.startAnimation(scaleAnimation)
}

/**
 * 扩展函数：安全地观察LiveData
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer(t)
        }
    })
}