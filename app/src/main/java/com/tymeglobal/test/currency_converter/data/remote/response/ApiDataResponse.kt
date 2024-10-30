package com.tymeglobal.test.currency_converter.data.remote.response

import com.google.gson.annotations.SerializedName

data class ApiDataResponse<T>(
    @SerializedName("statusCode")
    val statusCode: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T
)
