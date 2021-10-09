package com.elbarody.maxabchallange.data.model

data class CurrencyResponse(
    var success: Boolean? = null,
    var timestamp: Long? = null,
    var historical: Boolean? = null,
    var base: String? = null,
    var date: String? = null,
    var rates: Any? = null
)
