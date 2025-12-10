package com.randomjudge

import com.randomjudge.data.datasource.RandomDataSource
import com.randomjudge.domain.model.DecisionType
import com.randomjudge.domain.repository.RandomRepository
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any

/**
 * 随机仓库测试，验证决策生成逻辑
 */
class RandomRepositoryTest {
    private val repository: RandomRepository = RandomDataSource()

    @Test
    fun `generateDecision returns correct result for yes_no type`() {
        val result = repository.generateDecision("yes_no")
        assertTrue(result == "是" || result == "否")
    }

    @Test
    fun `generateDecision returns correct result for agree_disagree type`() {
        val result = repository.generateDecision("agree_disagree")
        assertTrue(result == "同意" || result == "拒绝")
    }

    @Test
    fun `generateDecision returns correct result with custom options`() {
        val customOptions = Pair("喜欢", "不喜欢")
        val result = repository.generateDecision("custom", customOptions)
        assertTrue(result == "喜欢" || result == "不喜欢")
    }

    @Test
    fun `getDecisionTypes returns non-empty list`() {
        val types = repository.getDecisionTypes()
        assertTrue(types.isNotEmpty())
        assertTrue(types.any { it.id == "yes_no" })
        assertTrue(types.any { it.id == "agree_disagree" })
    }

    @Test
    fun `generateYesNoDecision returns boolean value`() {
        val result = repository.generateYesNoDecision()
        assertTrue(result is Boolean)
    }

    @Test
    fun `generateMultipleChoiceDecision returns correct option`() {
        val options = listOf("Option 1", "Option 2", "Option 3")
        val result = repository.generateMultipleChoiceDecision(options)
        
        assertTrue(options.contains(result))
    }

    @Test
    fun `generateMultipleChoiceDecision returns default when empty options`() {
        val result = repository.generateMultipleChoiceDecision(emptyList())
        assertEquals("没有选项", result)
    }

    @Test
    fun `generatePercentageDecision returns value between 0 and 100`() {
        val result = repository.generatePercentageDecision()
        
        assertTrue(result >= 0)
        assertTrue(result <= 100)
    }
}