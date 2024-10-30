package com.tymeglobal.test.currency_converter.data.remote.api

object Endpoints {
    private const val VERSION = "v2.0"
    const val CURRENCY_LIST_URL = "/$VERSION/supported-currencies"
    const val CURRENCY_LATEST_URL = "/$VERSION/rates/latest"
}