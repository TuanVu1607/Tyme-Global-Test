package com.tymeglobal.test.currency_converter.domain.repository

import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.data.model.CurrencyInfoRates
import kotlinx.coroutines.flow.Flow

interface ExchangeCurrencyRepository {
    suspend fun getSelectedFromCurrency(): CurrencyInfoRates

    suspend fun setSelectedFromCurrency(currency: CurrencyInfoRates)

    suspend fun getSelectedToCurrency(): CurrencyInfoRates

    suspend fun setSelectedToCurrency(currency: CurrencyInfoRates)

    suspend fun getCurrencyLatestRatesFromDB(): Flow<List<CurrencyRatesEntity>>
}