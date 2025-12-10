package com.randomjudge.domain.repository

interface BackgroundRepository {
    suspend fun getBackgroundList(): List<String>
    suspend fun getCurrentBackground(): String
    suspend fun setBackgroundImage(background: String)
}