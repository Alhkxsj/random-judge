package com.randomjudge.domain.usecase

import com.randomjudge.domain.model.AppSettings
import com.randomjudge.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 获取应用设置用例
 */
class GetAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    /**
     * 获取应用设置流
     */
    fun execute(): Flow<AppSettings> {
        return settingsRepository.getAppSettingsFlow()
    }
    
    /**
     * 获取当前应用设置（一次性获取）
     */
    suspend fun getCurrentSettings(): AppSettings {
        return settingsRepository.getAppSettings()
    }
}