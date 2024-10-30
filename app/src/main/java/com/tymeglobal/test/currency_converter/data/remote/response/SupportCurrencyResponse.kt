package com.tymeglobal.test.currency_converter.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tymeglobal.test.currency_converter.data.model.CurrencyInfo

data class SupportCurrencyResponse(
    @SerializedName("supportedCurrenciesMap")
    var supportedCurrenciesMap: Map<String, CurrencyInfo> = mapOf()
)
