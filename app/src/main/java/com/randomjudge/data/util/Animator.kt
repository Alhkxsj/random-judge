package com.randomjudge.data.util

import android.content.Context
import android.view.View
import android.view.animation.*
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialContainerTransform
import com.randomjudge.R
import kotlinx.coroutines.*

/**
 * 动画控制器，处理结果展示动画
 */
class DecisionAnimator {
    /**
     * 执行三阶段决策动画
     */
    suspend fun animateDecisionProcess(
        buttonView: View,
        resultView: View,
        resultText: String
    ) {
        withContext(Dispatchers.Main) {
            // 1. 悬停等待阶段
            buttonView.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .start()
            
            delay(500)
            
            // 2. 结果展示阶段
            resultView.text = resultText
            resultView.alpha = 0f
            resultView.scaleX = 0.8f
            resultView.scaleY = 0.8f
            
            resultView.animate()
                .alpha(1f)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(400)
                .setInterpolator(OvershootInterpolator(1.5f))
                .withEndAction {
                    resultView.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200)
                        .start()
                }
                .start()
            
            // 3. 保持状态阶段
            delay(2000)
            
            // 恢复按钮状态
            buttonView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .start()
        }
    }

    /**
     * 设置Fragment间共享元素过渡
     */
    fun setupSharedElementTransitions(fragment: Fragment) {
        val transition = MaterialContainerTransform().apply {
            duration = 300
        }
        
        // This would be set on the fragment's enter and return transitions
        // fragment.sharedElementEnterTransition = transition
        // fragment.sharedElementReturnTransition = transition
    }
    
    /**
     * 应用缩放动画
     */
    fun applyScaleAnimation(view: View) {
        val scaleAnimation = AnimationUtils.loadAnimation(view.context, R.anim.scale_animation)
        view.startAnimation(scaleAnimation)
    }
    
    /**
     * 应用淡入动画
     */
    fun applyFadeInAnimation(view: View) {
        val fadeInAnimation = AnimationUtils.loadAnimation(view.context, R.anim.fade_in)
        view.startAnimation(fadeInAnimation)
    }
}