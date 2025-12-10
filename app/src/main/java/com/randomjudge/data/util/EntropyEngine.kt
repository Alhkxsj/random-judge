package com.randomjudge.data.util

import java.security.SecureRandom
import kotlin.random.Random

/**
 * 高熵多层混合随机引擎
 * 实现系统熵源、环境熵源和用户交互熵源的混合
 */
class EntropyEngine {
    private val secureRandom = SecureRandom.getInstanceStrong()
    private var userInteractionEntropy = mutableListOf<Long>()
    private var lastReseedTime = 0L
    private val RESEED_THRESHOLD = 1000 * 60 * 5 // 5分钟

    /**
     * 收集系统熵源
     */
    private fun collectSystemEntropy(): ByteArray {
        return SecureRandom.getInstanceStrong().generateSeed(16)
    }

    /**
     * 收集环境熵源
     */
    private fun collectEnvironmentEntropy(): ByteArray {
        val entropy = mutableListOf<Long>()
        entropy.add(System.currentTimeMillis())
        entropy.add(Runtime.getRuntime().freeMemory())
        entropy.add(Thread.currentThread().id)
        return entropy.toByteArray()
    }

    /**
     * 添加用户交互熵源
     */
    fun addInteractionEntropy(x: Float, y: Float, time: Long) {
        userInteractionEntropy.add(x.toLong())
        userInteractionEntropy.add(y.toLong())
        userInteractionEntropy.add(time)
    }

    /**
     * 重新播种随机数生成器
     */
    private fun reseed() {
        val entropy = mutableListOf<Byte>()
        entropy.addAll(collectSystemEntropy().toList())
        entropy.addAll(collectEnvironmentEntropy().toList())
        
        if (userInteractionEntropy.isNotEmpty()) {
            entropy.addAll(userInteractionEntropy.joinToString().toByteArray().toList())
            userInteractionEntropy.clear()
        }
        
        secureRandom.setSeed(entropy.toByteArray())
        lastReseedTime = System.currentTimeMillis()
    }

    /**
     * 检查是否需要重新播种
     */
    fun checkReseedNeeded() {
        if (System.currentTimeMillis() - lastReseedTime > RESEED_THRESHOLD) {
            reseed()
        }
    }

    /**
     * 生成随机决策
     * @param options 两个选项的列表
     * @return 随机选择的一个选项
     */
    fun generateDecision(options: List<String>): String {
        checkReseedNeeded()
        return options[secureRandom.nextInt(options.size)]
    }

    /**
     * 生成随机整数
     */
    fun generateRandomInt(min: Int, max: Int): Int {
        checkReseedNeeded()
        return secureRandom.nextInt(max - min) + min
    }
    
    /**
     * 生成随机布尔值
     */
    fun generateRandomBoolean(): Boolean {
        checkReseedNeeded()
        return secureRandom.nextBoolean()
    }
    
    /**
     * 生成随机字符串
     */
    fun generateRandomString(length: Int): String {
        checkReseedNeeded()
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars[secureRandom.nextInt(chars.length)] }
            .joinToString("")
    }
    
    /**
     * 洗牌列表
     */
    fun <T> shuffleList(list: List<T>): List<T> {
        checkReseedNeeded()
        val mutableList = list.toMutableList()
        for (i in mutableList.size - 1 downTo 1) {
            val j = secureRandom.nextInt(i + 1)
            val temp = mutableList[i]
            mutableList[i] = mutableList[j]
            mutableList[j] = temp
        }
        return mutableList
    }
}

/**
 * 扩展函数：将Long转换为ByteArray
 */
private fun Long.toByteArray(): ByteArray {
    val bytes = ByteArray(8)
    for (i in 0..7) {
        bytes[i] = (this shr (i * 8)).toByte()
    }
    return bytes
}

/**
 * 扩展函数：将List<Byte>转换为ByteArray
 */
private fun List<Byte>.toByteArray(): ByteArray {
    val array = ByteArray(this.size)
    for (i in this.indices) {
        array[i] = this[i]
    }
    return array
}