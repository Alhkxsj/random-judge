package com.randomjudge.di

import android.content.Context
import com.randomjudge.data.datasource.BackgroundDataSource
import com.randomjudge.data.datasource.RandomDataSource
import com.randomjudge.data.datasource.SettingsDataSource
import com.randomjudge.domain.repository.BackgroundRepository
import com.randomjudge.domain.repository.RandomRepository
import com.randomjudge.domain.repository.SettingsRepository
import com.randomjudge.domain.usecase.GetAppSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
class AppModule {
    
    @Provides
    @Singleton
    fun provideRandomRepository(): RandomRepository {
        return RandomDataSource()
    }
    
    @Provides
    @Singleton
    fun provideBackgroundRepository(): BackgroundRepository {
        return BackgroundDataSource()
    }
    
    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsDataSource(context)
    }
    
    @Provides
    @Singleton
    fun provideGetAppSettingsUseCase(settingsRepository: SettingsRepository): GetAppSettingsUseCase {
        return GetAppSettingsUseCase(settingsRepository)
    }
}