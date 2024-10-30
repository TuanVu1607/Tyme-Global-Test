package com.tymeglobal.test.currency_converter.data.local.prefs

import android.content.Context
import com.google.gson.Gson
import com.tymeglobal.test.currency_converter.data.model.CurrencyInfoRates
import com.tymeglobal.test.currency_converter.domain.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext context: Context) :
    SharedPreferences(context),
    UserPreferencesRepository {

    companion object {
        private const val KEYSTORE_NAME = "userCache"
        private const val KEY_SELECTED_FROM_CURRENCY = "selectedFromCurrency"
        private const val KEY_SELECTED_TO_CURRENCY = "selectedToCurrency"
    }

    override fun getPrefName() = KEYSTORE_NAME

    override fun getSelectedFromCurrency(): CurrencyInfoRates =
        preferences.getString(KEY_SELECTED_FROM_CURRENCY, "")
            ?.let { Gson().fromJson(it, CurrencyInfoRates::class.java) }
            ?: CurrencyInfoRates()

    override fun setSelectedFromCurrency(currency: CurrencyInfoRates) =
        preferences.edit().putString(KEY_SELECTED_FROM_CURRENCY, Gson().toJson(currency)).apply()

    override fun getSelectedToCurrency(): CurrencyInfoRates =
        preferences.getString(KEY_SELECTED_TO_CURRENCY, "")
            ?.let { Gson().fromJson(it, CurrencyInfoRates::class.java) }
            ?: CurrencyInfoRates()

    override fun setSelectedToCurrency(currency: CurrencyInfoRates) =
        preferences.edit().putString(KEY_SELECTED_TO_CURRENCY, Gson().toJson(currency)).apply()
}