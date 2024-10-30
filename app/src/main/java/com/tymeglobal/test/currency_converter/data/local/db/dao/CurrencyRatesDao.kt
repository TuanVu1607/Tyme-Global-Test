package com.tymeglobal.test.currency_converter.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(currencyRates: CurrencyRatesEntity): Long

    @Update
    suspend fun update(currencyRates: CurrencyRatesEntity): Int

    @Delete
    suspend fun delete(currencyRates: CurrencyRatesEntity): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<CurrencyRatesEntity>): List<Long>

    @Query("DELETE FROM currency_rates")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM currency_rates")
    fun getAll(): List<CurrencyRatesEntity>
}