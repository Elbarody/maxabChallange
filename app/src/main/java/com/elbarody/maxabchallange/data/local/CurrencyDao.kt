package com.elbarody.maxabchallange.data.local

import androidx.room.*
import com.elbarody.maxabchallange.data.model.CurrencyModel

@Dao
interface CurrencyDao {
    @Query("select * from RatesModel")
    suspend fun getAllRates(): RatesModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(vararg ratesModel: RatesModel)


}