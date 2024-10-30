package com.tymeglobal.test.currency_converter.di.modules

import com.tymeglobal.test.currency_converter.di.annotations.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}