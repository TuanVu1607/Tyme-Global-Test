package com.tymeglobal.test.currency_converter.data.remote.api

import com.tymeglobal.test.currency_converter.data.remote.response.SupportCurrencyResponse
import com.tymeglobal.test.currency_converter.data.remote.response.ApiDataResponse
import com.tymeglobal.test.currency_converter.data.remote.response.LatestCurrencyRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Endpoints.CURRENCY_LIST_URL)
    suspend fun getCurrencies(
        @Query("apikey") appId: String
    ): SupportCurrencyResponse

    @GET(Endpoints.CURRENCY_LATEST_URL)
    suspend fun getLatestCurrencyRates(
        @Query("apikey") appId: String,
        @Query("base") symbols: String
    ): LatestCurrencyRatesResponse
}