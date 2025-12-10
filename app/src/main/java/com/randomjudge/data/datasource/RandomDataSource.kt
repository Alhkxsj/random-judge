package com.randomjudge.data.datasource

import com.randomjudge.data.util.EntropyEngine
import com.randomjudge.domain.model.DecisionType
import com.randomjudge.domain.repository.RandomRepository

/**
 * 随机数据源实现，使用高熵引擎生成随机决策
 */
/**
 * 随机数据源实现，使用高熵引擎生成随机决策
 */
class RandomDataSource : RandomRepository {
    private val entropyEngine = EntropyEngine()
    
    private val decisionTypes = listOf(
        DecisionType("yes_no", "是" to "否"),
        DecisionType("agree_disagree", "同意" to "拒绝"),
        DecisionType("win_lose", "赢" to "输"),
        DecisionType("true_false", "真" to "假")
    )

    override suspend fun generateDecision(type: String, customOptions: Pair<String, String>?): String {
        val options = when {
            customOptions != null && type == "custom" -> listOf(customOptions.first, customOptions.second)
            else -> {
                val decisionType = decisionTypes.find { it.id == type }
                listOf(decisionType?.options?.first ?: "是", decisionType?.options?.second ?: "否")
            }
        }
        return entropyEngine.generateDecision(options)
    }

    override suspend fun getDecisionTypes(): List<DecisionType> {
        return decisionTypes
    }

    override fun addInteractionEntropy(x: Float, y: Float, time: Long) {
        entropyEngine.addInteractionEntropy(x, y, time)
    }

    override suspend fun generateYesNoDecision(): Boolean {
        return entropyEngine.generateRandomBoolean()
    }

    override suspend fun generateMultipleChoiceDecision(options: List<String>): String {
        return if (options.isNotEmpty()) {
            entropyEngine.generateDecision(options)
        } else {
            "没有选项"
        }
    }

    override suspend fun generatePercentageDecision(): Int {
        return entropyEngine.generateRandomInt(0, 101)
    }
}