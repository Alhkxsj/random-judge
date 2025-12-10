package com.randomjudge.domain.model

/**
 * 决策类型数据类，支持预设类型和自定义选项
 */
data class DecisionType(
    val id: String,
    val options: Pair<String, String>
)