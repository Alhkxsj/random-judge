package com.randomjudge.domain.model

data class AppSettings(
    val theme: String = "light",
    val background: String = "default",
    val animationEnabled: Boolean = true,
    val soundEnabled: Boolean = true
)