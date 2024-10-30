package com.tymeglobal.test.currency_converter.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyInfoRates(
    @SerializedName("currencyCode")
    var currencyCode: String = "",
    @SerializedName("currencyName")
    var currencyName: String = "",
    @SerializedName("countryCode")
    var countryCode: String = "",
    @SerializedName("countryName")
    var countryName: String = "",
    @SerializedName("icon")
    var icon: String = "",
    @SerializedName("rate")
    val rate: Double = 0.0,
    @SerializedName("amount")
    val amount: String = "0"
)