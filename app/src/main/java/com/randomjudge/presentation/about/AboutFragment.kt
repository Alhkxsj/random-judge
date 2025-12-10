package com.randomjudge.presentation.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.randomjudge.BuildConfig
import com.randomjudge.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 关于界面Fragment，显示应用信息
 */
@AndroidEntryPoint
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: AboutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            // 应用版本
            tvVersion?.text = "版本 ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
            
            // 作者信息
            tvAuthor?.text = "快手阿泠好困想睡觉"
            
            // 开发初心
            tvDescription?.text = "突发奇想，随便写了一个，不喜勿喷。"
            
            // 技术栈
            tvTechStack?.text = "Kotlin | MVVM | Material Design 3 | Jetpack"
            
            // 开源协议
            tvLicense?.text = "开源协议: MIT"
            
            // GitHub链接 (would need clickable implementation)
            tvGithubLink?.text = "GitHub: https://github.com/yourusername/random-judge"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}