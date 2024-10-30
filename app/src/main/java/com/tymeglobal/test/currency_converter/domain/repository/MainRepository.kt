package com.tymeglobal.test.currency_converter.domain.repository

import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.data.remote.response.LatestCurrencyRatesResponse
import com.tymeglobal.test.currency_converter.data.remote.response.SupportCurrencyResponse
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getSupportCurrencyList(): Flow<SupportCurrencyResponse>

    suspend fun getCurrencyLatestRates(base: String): Flow<LatestCurrencyRatesResponse>

    suspend fun deleteAllSupportCurrencyList(): Int

    suspend fun saveSupportCurrencyList(list: List<CurrencyInfoEntity>): List<Long>

    suspend fun getSupportCurrencyListFromDB(): Flow<List<CurrencyInfoEntity>>

    suspend fun deleteAllCurrencyLatestRates(): Int

    suspend fun saveCurrencyLatestRates(list: List<CurrencyRatesEntity>): List<Long>
}