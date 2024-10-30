package com.tymeglobal.test.currency_converter.common.extensions

import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import java.math.BigDecimal
import java.util.Locale

fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.uppercase(): String = uppercase(Locale.getDefault())

fun String.lowercase(): String = lowercase(Locale.getDefault())

fun String.debug(message: String) {
    LoggerUtils.d(this, message)
}

fun String.removeNonAlphanumeric(): String = this.replace(Regex("[^\\w\\s]"), "")
