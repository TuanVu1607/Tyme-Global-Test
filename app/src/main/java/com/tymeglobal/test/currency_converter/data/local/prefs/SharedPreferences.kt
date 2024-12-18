package com.tymeglobal.test.currency_converter.data.local.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

abstract class SharedPreferences(context: Context) {
    abstract fun getPrefName(): String

    protected val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(getPrefName(), MODE_PRIVATE)
    }
}