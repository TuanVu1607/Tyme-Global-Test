package com.tymeglobal.test.currency_converter.common.interfaces

import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity

interface OnCurrencySelectListener {
    fun onCurrencySelected(currency: CurrencyInfoEntity, isFromCurrency: Boolean)
}