package com.tymeglobal.test.currency_converter.data.repository

import com.tymeglobal.test.currency_converter.BuildConfig
import com.tymeglobal.test.currency_converter.data.local.db.AppDatabase
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.data.remote.api.ApiService
import com.tymeglobal.test.currency_converter.data.remote.response.LatestCurrencyRatesResponse
import com.tymeglobal.test.currency_converter.data.remote.response.SupportCurrencyResponse
import com.tymeglobal.test.currency_converter.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : MainRepository {
    override suspend fun getSupportCurrencyList(): Flow<SupportCurrencyResponse> =
        flow {
            emit(apiService.getCurrencies(BuildConfig.APP_ID_KEY))
        }

    override suspend fun getCurrencyLatestRates(base: String): Flow<LatestCurrencyRatesResponse> =
        flow {
            emit(apiService.getLatestCurrencyRates(BuildConfig.APP_ID_KEY, base))
        }

    override suspend fun deleteAllSupportCurrencyList(): Int =
        appDatabase.currencyInfoDao().deleteAll()

    override suspend fun saveSupportCurrencyList(list: List<CurrencyInfoEntity>): List<Long> =
        appDatabase.currencyInfoDao().insertAll(list)

    override suspend fun getSupportCurrencyListFromDB(): Flow<List<CurrencyInfoEntity>> =
        flow {
            emit(appDatabase.currencyInfoDao().getAll())
        }

    override suspend fun deleteAllCurrencyLatestRates(): Int =
        appDatabase.currencyRatesDao().deleteAll()

    override suspend fun saveCurrencyLatestRates(list: List<CurrencyRatesEntity>): List<Long> =
        appDatabase.currencyRatesDao().insertAll(list)
}