package com.tymeglobal.test.currency_converter.common.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun String.convertMoneyStringToBigDecimal(): BigDecimal {
    // Normalize the string
    val normalized: String = this
        .replace(".", "") // Remove all periods (grouping separators)
        .replace(",", ".") // Replace comma with dot (decimal separator)
    // Create a BigDecimal from the normalized string
    return BigDecimal(normalized)
}

fun BigDecimal.convertToMoneyString(): String {
    // Create a DecimalFormat with the desired format for output
    val symbols = DecimalFormatSymbols(Locale.getDefault())
    symbols.setDecimalSeparator(',') // Set comma as decimal separator
    symbols.setGroupingSeparator('.') // Set dot as grouping separator

    val format = DecimalFormat("#,##0.####", symbols)

    return format.format(this)
}