package com.tymeglobal.test.currency_converter.data.remote.response

import com.google.gson.annotations.SerializedName

data class LatestCurrencyRatesResponse(
    @SerializedName("date")
    var date: String = "",
    @SerializedName("base")
    var base: String = "",
    @SerializedName("rates")
    var rates: Map<String, String>
)