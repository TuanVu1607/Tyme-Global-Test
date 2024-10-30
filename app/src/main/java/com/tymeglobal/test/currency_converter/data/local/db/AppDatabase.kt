package com.tymeglobal.test.currency_converter.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tymeglobal.test.currency_converter.data.local.db.dao.CurrencyInfoDao
import com.tymeglobal.test.currency_converter.data.local.db.dao.CurrencyRatesDao
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.di.annotations.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        CurrencyInfoEntity::class,
        CurrencyRatesEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyInfoDao(): CurrencyInfoDao
    abstract fun currencyRatesDao(): CurrencyRatesDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}