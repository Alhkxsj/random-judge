package com.randomjudge.data.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.randomjudge.R

/**
 * 背景管理系统，处理背景切换和应用
 */
class BackgroundManager {
    enum class Mode { SYSTEM, CUSTOM_IMAGE, SOLID_COLOR }

    private var currentMode = Mode.SYSTEM
    private var customImagePath: String? = null
    private var solidColor = Color.WHITE

    /**
     * 应用背景到视图
     */
    fun applyToView(context: Context, rootView: View) {
        when (currentMode) {
            Mode.SYSTEM -> {
                rootView.setBackgroundResource(0)
                rootView.setBackgroundColor(Color.TRANSPARENT)
            }
            Mode.CUSTOM_IMAGE -> {
                customImagePath?.let { imagePath ->
                    Glide.with(context)
                        .load(imagePath)
                        .transform(CenterCrop()) // Simplified for now; would need blur transformation library
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                rootView.background = resource
                                applyOverlay(rootView)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                rootView.background = placeholder
                            }
                        })
                }
            }
            Mode.SOLID_COLOR -> {
                rootView.background = null
                rootView.setBackgroundColor(solidColor)
            }
        }
    }

    /**
     * 应用覆盖层确保文字可读性
     */
    private fun applyOverlay(rootView: View) {
        // This would typically be implemented with a semi-transparent overlay
        // For now, we'll just use the existing bg_overlay drawable
        rootView.setBackgroundResource(R.drawable.bg_overlay)
    }

    /**
     * 选择图片从相册
     */
    fun selectImageFromGallery(activity: AppCompatActivity, launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(intent)
    }

    /**
     * 保存背景设置
     */
    fun saveSettings(mode: Mode, imagePath: String? = null, color: Int = Color.WHITE) {
        currentMode = mode
        customImagePath = imagePath
        solidColor = color
    }

    /**
     * 获取当前背景模式
     */
    fun getCurrentMode(): Mode {
        return currentMode
    }

    /**
     * 获取自定义图片路径
     */
    fun getCustomImagePath(): String? {
        return customImagePath
    }

    companion object {
        const val REQUEST_IMAGE_PICK = 1001
    }
}