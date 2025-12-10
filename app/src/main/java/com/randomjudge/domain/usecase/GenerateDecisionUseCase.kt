package com.randomjudge.domain.usecase

import com.randomjudge.domain.repository.RandomRepository
import javax.inject.Inject

/**
 * 生成决策用例，封装决策生成逻辑
 */
class GenerateDecisionUseCase @Inject constructor(
    private val randomRepository: RandomRepository
) {
    /**
     * 执行决策生成
     * @param decisionType 决策类型ID
     * @param options 决策选项（用于自定义决策）
     * @return 生成的决策结果
     */
    suspend fun execute(decisionType: String, options: List<String> = emptyList()): String {
        return when (decisionType) {
            "yes_no", "agree_disagree", "win_lose", "true_false" -> {
                randomRepository.generateDecision(decisionType)
            }
            "multiple_choice" -> {
                if (options.size >= 2) {
                    // For multiple options, we can use the entropy engine to make a selection
                    val optionPairs = Pair(options[0], options.getOrElse(1) { options[0] })
                    randomRepository.generateDecision("custom", optionPairs)
                } else {
                    randomRepository.generateDecision(decisionType)
                }
            }
            "percentage" -> "${randomRepository.generatePercentageDecision()}%"
            "custom" -> {
                if (options.size >= 2) {
                    val customOptions = Pair(options[0], options[1])
                    randomRepository.generateDecision(decisionType, customOptions)
                } else {
                    "自定义选项不足"
                }
            }
            else -> "无效的决策类型"
        }
    }
    
    /**
     * 添加交互熵源
     */
    fun addInteractionEntropy(x: Float, y: Float, time: Long) {
        randomRepository.addInteractionEntropy(x, y, time)
    }
}