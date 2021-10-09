package com.elbarody.maxabchallange.data.repo.currency_data

import com.elbarody.maxabchallange.data.model.CurrencyResponse
import com.elbarody.maxabchallange.data.remote.CurrencyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class CurrencyDataRepositoryImp @Inject constructor(private val api: CurrencyApi) :
    CurrencyDataRepository {


    override suspend fun getCurrencyData(date: String): Flow<Response<CurrencyResponse>> {
        return flow {
            emit(
                api.getCurrencyData(date)
            )
        }.flowOn(Dispatchers.IO)
    }
}