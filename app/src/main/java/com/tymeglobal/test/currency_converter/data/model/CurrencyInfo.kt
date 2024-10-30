package com.tymeglobal.test.currency_converter.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyInfo(
    @SerializedName("currencyCode")
    var currencyCode: String = "",
    @SerializedName("currencyName")
    var currencyName: String = "",
    @SerializedName("countryCode")
    var countryCode: String = "",
    @SerializedName("countryName")
    var countryName: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("availableFrom")
    var availableFrom: String = "",
    @SerializedName("icon")
    var icon: String = ""
)
