package com.randomjudge.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.randomjudge.data.util.BackgroundManager
import com.randomjudge.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 设置界面Fragment，处理背景和决策类型设置
 */
@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by viewModels()
    private val backgroundManager = BackgroundManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            // 背景模式选择
            rgBackgroundMode?.setOnCheckedChangeListener { _, checkedId ->
                val mode = when (checkedId) {
                    R.id.rb_system -> BackgroundManager.Mode.SYSTEM
                    R.id.rb_custom_image -> BackgroundManager.Mode.CUSTOM_IMAGE
                    R.id.rb_solid_color -> BackgroundManager.Mode.SOLID_COLOR
                    else -> BackgroundManager.Mode.SYSTEM
                }
                viewModel.setBackgroundMode(mode)
            }
            
            // 图片选择按钮
            btnSelectImage?.setOnClickListener {
                // This would require activity result launcher to be passed in
                // For now, placeholder implementation
            }
            
            // 决策类型选择
            spDecisionType?.let { spinner ->
                // This would require a DecisionTypeAdapter to be implemented
                // For now, we'll use placeholder implementation
            }
            
            // Animation and sound switches
            switchAnimation?.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setAnimationEnabled(isChecked)
            }
            
            switchSound?.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setSoundEnabled(isChecked)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.settings.collect { settings ->
                    // Update UI based on settings
                    binding.switchAnimation?.isChecked = settings.animationEnabled
                    binding.switchSound?.isChecked = settings.soundEnabled
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}