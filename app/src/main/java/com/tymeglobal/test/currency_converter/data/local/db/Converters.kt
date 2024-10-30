package com.tymeglobal.test.currency_converter.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date {
        return value?.let { Date(it) } ?: Date(System.currentTimeMillis())
    }

    @TypeConverter
    fun toTimestamp(value: Date?): Long {
        return value?.let { value.time } ?: System.currentTimeMillis()
    }

    @TypeConverter
    fun fromCurrencyInfoEntity(value: CurrencyInfoEntity?): String {
        return value?.let { Gson().toJson(value) } ?: ""
    }

    @TypeConverter
    fun toCurrencyInfoEntity(value: String?): CurrencyInfoEntity {
        return value?.let { Gson().fromJson(it, CurrencyInfoEntity::class.java) }
            ?: CurrencyInfoEntity()
    }

    @TypeConverter
    fun fromCurrencyRatesEntity(value: CurrencyRatesEntity?): String {
        return value?.let { Gson().toJson(value) } ?: ""
    }

    @TypeConverter
    fun toCurrencyRatesEntity(value: String?): CurrencyRatesEntity {
        return value?.let { Gson().fromJson(it, CurrencyRatesEntity::class.java) }
            ?: CurrencyRatesEntity()
    }
}