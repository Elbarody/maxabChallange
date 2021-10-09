package com.elbarody.maxabchallange.data.remote

import com.elbarody.maxabchallange.data.model.CurrencyResponse
import com.elbarody.maxabchallange.network_layer.NetWorkConstant.ACCESS_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {
    @GET("api/{date}")
    suspend fun getCurrencyData(
        @Path("date") date: String,
        @Query("access_key") access_key: String = ACCESS_TOKEN,
        @Query("base") base: String = "EUR"
    ): Response<CurrencyResponse>

}