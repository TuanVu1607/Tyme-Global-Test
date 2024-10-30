package com.tymeglobal.test.currency_converter.domain.repository

import com.tymeglobal.test.currency_converter.data.model.CurrencyInfoRates

interface UserPreferencesRepository {
    fun getSelectedFromCurrency(): CurrencyInfoRates

    fun setSelectedFromCurrency(currency: CurrencyInfoRates)

    fun getSelectedToCurrency(): CurrencyInfoRates

    fun setSelectedToCurrency(currency: CurrencyInfoRates)
}