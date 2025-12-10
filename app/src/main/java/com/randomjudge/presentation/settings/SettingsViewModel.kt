package com.randomjudge.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomjudge.data.util.BackgroundManager
import com.randomjudge.domain.model.AppSettings
import com.randomjudge.domain.repository.SettingsRepository
import com.randomjudge.domain.usecase.GetAppSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 设置ViewModel，管理应用设置状态
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val getAppSettingsUseCase: GetAppSettingsUseCase
) : ViewModel() {
    
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            try {
                val settings = getAppSettingsUseCase.getCurrentSettings()
                _settings.value = settings
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 更新设置
     */
    fun updateSettings(settings: AppSettings) {
        viewModelScope.launch {
            try {
                settingsRepository.updateAppSettings(settings)
                _settings.value = settings
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 设置背景模式
     */
    fun setBackgroundMode(mode: BackgroundManager.Mode) {
        viewModelScope.launch {
            try {
                settingsRepository.setBackgroundMode(mode)
                // Update local state
                val currentSettings = _settings.value
                _settings.value = currentSettings.copy(background = mode.name.lowercase())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 设置动画启用状态
     */
    fun setAnimationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _settings.value.copy(animationEnabled = enabled)
                settingsRepository.updateAppSettings(currentSettings)
                _settings.value = currentSettings
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 设置声音启用状态
     */
    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _settings.value.copy(soundEnabled = enabled)
                settingsRepository.updateAppSettings(currentSettings)
                _settings.value = currentSettings
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}