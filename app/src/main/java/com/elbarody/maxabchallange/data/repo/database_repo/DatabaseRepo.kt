package com.elbarody.maxabchallange.data.repo.database_repo

import com.elbarody.maxabchallange.data.local.CurrencyDao
import com.elbarody.maxabchallange.data.local.RatesModel
import javax.inject.Inject

class DatabaseRepo@Inject constructor(
    private val dao: CurrencyDao
){

    suspend fun addCurrencyToDataBase(ratesModel: RatesModel) = dao.addCurrency(ratesModel)
    suspend fun getCurrencyFromDataBase() = dao.getAllRates()
}