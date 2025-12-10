package com.randomjudge.util

object Constants {
    // Preference constants
    const val PREFS_NAME = "random_judge_prefs"
    const val THEME_KEY = "theme"
    const val BACKGROUND_KEY = "background"
    const val ANIMATION_KEY = "animation_enabled"
    const val SOUND_KEY = "sound_enabled"
    
    // Request codes
    const val REQUEST_IMAGE_PICK = 1001
    
    // Default values
    const val DEFAULT_THEME = "light"
    const val DEFAULT_BACKGROUND = "default"
    const val DEFAULT_ANIMATION_ENABLED = true
    const val DEFAULT_SOUND_ENABLED = true
    
    // Decision types
    const val DECISION_TYPE_YES_NO = "yes_no"
    const val DECISION_TYPE_AGREE_DISAGREE = "agree_disagree"
    const val DECISION_TYPE_WIN_LOSE = "win_lose"
    const val DECISION_TYPE_TRUE_FALSE = "true_false"
    const val DECISION_TYPE_CUSTOM = "custom"
    
    // Animation durations
    const val ANIMATION_DURATION_SHORT = 300L
    const val ANIMATION_DURATION_MEDIUM = 500L
    const val ANIMATION_DURATION_LONG = 800L
}