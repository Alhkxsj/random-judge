package com.randomjudge.domain.repository

import com.randomjudge.data.util.BackgroundManager
import com.randomjudge.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * 设置仓库，处理应用设置的存储和获取
 */
interface SettingsRepository {
    /**
     * 获取应用设置
     */
    suspend fun getAppSettings(): AppSettings
    
    /**
     * 获取应用设置流
     */
    fun getAppSettingsFlow(): Flow<AppSettings>

    /**
     * 更新应用设置
     */
    suspend fun updateAppSettings(settings: AppSettings)
    
    /**
     * 设置背景模式
     */
    suspend fun setBackgroundMode(mode: BackgroundManager.Mode)
    
    /**
     * 设置自定义图片背景
     */
    suspend fun setCustomImageBackground(imagePath: String)
    
    /**
     * 设置选中的决策类型
     */
    suspend fun setSelectedDecisionType(decisionTypeId: String)
}