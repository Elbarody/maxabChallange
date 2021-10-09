package com.elbarody.maxabchallange.data.repo.currency_data

import com.elbarody.maxabchallange.data.model.CurrencyResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CurrencyDataRepository {
    suspend fun getCurrencyData(
        date: String
    ): Flow<Response<CurrencyResponse>>
}