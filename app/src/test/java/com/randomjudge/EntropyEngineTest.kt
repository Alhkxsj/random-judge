package com.randomjudge

import com.randomjudge.data.util.EntropyEngine
import org.junit.Test
import org.junit.Assert.*
import kotlin.math.abs

/**
 * 随机引擎测试，验证随机性和熵源处理
 */
class EntropyEngineTest {
    private val entropyEngine = EntropyEngine()

    @Test
    fun `generateDecision returns random result`() {
        val options = listOf("是", "否")
        val results = mutableListOf<String>()
        
        // 生成100个结果
        repeat(100) {
            results.add(entropyEngine.generateDecision(options))
        }
        
        // 检查结果分布是否接近50/50
        val yesCount = results.count { it == "是" }
        val noCount = results.count { it == "否" }
        
        assertTrue("结果分布应接近50/50，实际是: $yesCount/$noCount") {
            abs(yesCount - noCount) < 40 // Allow some variance but not too much
        }
    }

    @Test
    fun `generateRandomInt returns value in range`() {
        val result = entropyEngine.generateRandomInt(1, 10)
        
        assertTrue(result >= 1)
        assertTrue(result < 10)
    }

    @Test
    fun `generateRandomBoolean returns boolean value`() {
        val result = entropyEngine.generateRandomBoolean()
        assertTrue(result is Boolean)
    }

    @Test
    fun `generateRandomString returns correct length string`() {
        val result = entropyEngine.generateRandomString(10)
        
        assertEquals(10, result.length)
    }

    @Test
    fun `shuffleList returns list with same elements`() {
        val originalList = listOf(1, 2, 3, 4, 5)
        val shuffledList = entropyEngine.shuffleList(originalList)
        
        assertEquals(originalList.size, shuffledList.size)
        assertTrue(originalList.all { it in shuffledList })
        assertTrue(shuffledList.all { it in originalList })
    }
}