package com.tymeglobal.test.currency_converter.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyInfoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(currencyInfo: CurrencyInfoEntity): Long

    @Update
    suspend fun update(currencyInfo: CurrencyInfoEntity): Int

    @Delete
    suspend fun delete(currencyInfo: CurrencyInfoEntity): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(list: List<CurrencyInfoEntity>): List<Long>

    @Query("DELETE FROM currency_info")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM currency_info")
    fun getAll(): List<CurrencyInfoEntity>
}