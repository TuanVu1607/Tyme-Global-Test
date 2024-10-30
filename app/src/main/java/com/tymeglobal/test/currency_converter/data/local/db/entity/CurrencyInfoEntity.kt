package com.tymeglobal.test.currency_converter.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency_info")
data class CurrencyInfoEntity (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("currencyCode")
    var currencyCode: String = "",
    @SerializedName("currencyName")
    var currencyName: String = "",
    @SerializedName("countryCode")
    var countryCode: String = "",
    @SerializedName("countryName")
    var countryName: String = "",
    @SerializedName("icon")
    var icon: String = ""
){
    fun getCurrencyFullUnit(): String {
        return "$currencyName ($currencyCode)"
    }
}