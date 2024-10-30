package com.tymeglobal.test.currency_converter.data.repository

import com.tymeglobal.test.currency_converter.data.local.db.AppDatabase
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.data.local.prefs.UserPreferences
import com.tymeglobal.test.currency_converter.data.model.CurrencyInfoRates
import com.tymeglobal.test.currency_converter.data.remote.api.ApiService
import com.tymeglobal.test.currency_converter.domain.repository.ExchangeCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeCurrencyRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val userPreferences: UserPreferences
) : ExchangeCurrencyRepository {
    override suspend fun getSelectedFromCurrency() = userPreferences.getSelectedFromCurrency()

    override suspend fun setSelectedFromCurrency(currency: CurrencyInfoRates) =
        userPreferences.setSelectedFromCurrency(currency)

    override suspend fun getSelectedToCurrency() = userPreferences.getSelectedToCurrency()

    override suspend fun setSelectedToCurrency(currency: CurrencyInfoRates) =
        userPreferences.setSelectedToCurrency(currency)

    override suspend fun getCurrencyLatestRatesFromDB(): Flow<List<CurrencyRatesEntity>> =
        flow {
            emit(appDatabase.currencyRatesDao().getAll())
        }
}