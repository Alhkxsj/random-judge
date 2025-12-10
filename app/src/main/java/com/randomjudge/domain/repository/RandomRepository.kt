package com.randomjudge.domain.repository

import com.randomjudge.domain.model.DecisionType
import kotlinx.coroutines.flow.Flow

/**
 * 随机决策仓库，封装随机算法逻辑
 */
interface RandomRepository {
    /**
     * 生成随机决策
     * @param type 决策类型ID
     * @param customOptions 自定义选项（可选）
     * @return 随机选择的结果
     */
    suspend fun generateDecision(type: String, customOptions: Pair<String, String>? = null): String

    /**
     * 获取所有预设决策类型
     */
    suspend fun getDecisionTypes(): List<DecisionType>

    /**
     * 添加用户交互熵源
     */
    fun addInteractionEntropy(x: Float, y: Float, time: Long)
    
    // Original methods
    suspend fun generateYesNoDecision(): Boolean
    suspend fun generateMultipleChoiceDecision(options: List<String>): String
    suspend fun generatePercentageDecision(): Int
}