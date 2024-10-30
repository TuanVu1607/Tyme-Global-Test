package com.tymeglobal.test.currency_converter.di.modules

import com.tymeglobal.test.currency_converter.common.process.Loader
import com.tymeglobal.test.currency_converter.data.local.db.AppDatabase
import com.tymeglobal.test.currency_converter.data.local.prefs.UserPreferences
import com.tymeglobal.test.currency_converter.data.remote.api.ApiService
import com.tymeglobal.test.currency_converter.data.repository.ExchangeCurrencyRepositoryImp
import com.tymeglobal.test.currency_converter.data.repository.MainRepositoryImp
import com.tymeglobal.test.currency_converter.domain.repository.ExchangeCurrencyRepository
import com.tymeglobal.test.currency_converter.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    internal fun provideMainRepository(
        apiService: ApiService,
        appDatabase: AppDatabase
    ): MainRepository {
        return MainRepositoryImp(apiService, appDatabase)
    }

    @Provides
    internal fun provideExchangeCurrencyRepository(
        apiService: ApiService,
        appDatabase: AppDatabase,
        userPreferences: UserPreferences
    ): ExchangeCurrencyRepository {
        return ExchangeCurrencyRepositoryImp(apiService, appDatabase, userPreferences)
    }
}