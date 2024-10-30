package com.tymeglobal.test.currency_converter.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency_rates")
data class CurrencyRatesEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("currencyCode")
    val currencyCode: String = "",
    @SerializedName("rate")
    val rate: Double = 0.0
)