package com.randomjudge.data.datasource

import com.randomjudge.domain.repository.BackgroundRepository

/**
 * 背景数据源实现
 */
class BackgroundDataSource : BackgroundRepository {
    private val backgrounds = listOf(
        "default",
        "nature",
        "city",
        "abstract",
        "ocean",
        "mountain"
    )
    
    private var currentBackground = "default"

    override suspend fun getBackgroundList(): List<String> {
        return backgrounds
    }

    override suspend fun getCurrentBackground(): String {
        return currentBackground
    }

    override suspend fun setBackgroundImage(background: String) {
        if (background in backgrounds) {
            currentBackground = background
        }
    }
}