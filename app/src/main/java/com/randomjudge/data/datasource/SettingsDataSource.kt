package com.randomjudge.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.randomjudge.data.util.BackgroundManager
import com.randomjudge.domain.model.AppSettings
import com.randomjudge.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * 设置数据源，使用DataStore存储设置
 */
/**
 * 设置数据源，使用DataStore存储设置
 */
class SettingsDataSource(private val context: Context) : SettingsRepository {
    private val BACKGROUND_MODE_KEY = stringPreferencesKey("background_mode")
    private val CUSTOM_IMAGE_PATH_KEY = stringPreferencesKey("custom_image_path")
    private val SELECTED_DECISION_TYPE_KEY = stringPreferencesKey("selected_decision_type")
    private val THEME_KEY = stringPreferencesKey("theme")
    private val ANIMATION_ENABLED_KEY = stringPreferencesKey("animation_enabled")
    private val SOUND_ENABLED_KEY = stringPreferencesKey("sound_enabled")

    override suspend fun getAppSettings(): AppSettings {
        val preferences = context.dataStore.data.first()
        
        val theme = preferences[THEME_KEY] ?: "light"
        val background = preferences[CUSTOM_IMAGE_PATH_KEY] ?: "default"
        val animationEnabled = preferences[ANIMATION_ENABLED_KEY] ?: true
        val soundEnabled = preferences[SOUND_ENABLED_KEY] ?: true
        
        return AppSettings(
            theme = theme,
            background = background,
            animationEnabled = animationEnabled,
            soundEnabled = soundEnabled
        )
    }

    override fun getAppSettingsFlow(): Flow<AppSettings> {
        return context.dataStore.data.map { preferences ->
            val theme = preferences[THEME_KEY] ?: "light"
            val background = preferences[CUSTOM_IMAGE_PATH_KEY] ?: "default"
            val animationEnabled = preferences[ANIMATION_ENABLED_KEY] ?: true
                val soundEnabled = preferences[SOUND_ENABLED_KEY] ?: true
            
            AppSettings(
                theme = theme,
                background = background,
                animationEnabled = animationEnabled,
                soundEnabled = soundEnabled
            )
        }
    }

    override suspend fun updateAppSettings(settings: AppSettings) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = settings.theme
            preferences[CUSTOM_IMAGE_PATH_KEY] = settings.background
            preferences[ANIMATION_ENABLED_KEY] = settings.animationEnabled
            preferences[SOUND_ENABLED_KEY] = settings.soundEnabled
        }
    }

    override suspend fun setBackgroundMode(mode: BackgroundManager.Mode) {
        // For now, we'll just update the background string
        val backgroundString = when (mode) {
            BackgroundManager.Mode.SYSTEM -> "system"
            BackgroundManager.Mode.CUSTOM_IMAGE -> "custom_image"
            BackgroundManager.Mode.SOLID_COLOR -> "solid_color"
        }
        context.dataStore.edit { preferences ->
            preferences[CUSTOM_IMAGE_PATH_KEY] = backgroundString
        }
    }

    override suspend fun setCustomImageBackground(imagePath: String) {
        context.dataStore.edit { preferences ->
            preferences[CUSTOM_IMAGE_PATH_KEY] = imagePath
        }
    }

    override suspend fun setSelectedDecisionType(decisionTypeId: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_DECISION_TYPE_KEY] = decisionTypeId
        }
    }
}