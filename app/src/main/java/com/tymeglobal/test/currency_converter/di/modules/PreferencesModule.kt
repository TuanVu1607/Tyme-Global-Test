package com.tymeglobal.test.currency_converter.di.modules

import android.content.Context
import com.tymeglobal.test.currency_converter.data.local.prefs.UserPreferences
import com.tymeglobal.test.currency_converter.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferencesRepository =
        UserPreferences(context)
}