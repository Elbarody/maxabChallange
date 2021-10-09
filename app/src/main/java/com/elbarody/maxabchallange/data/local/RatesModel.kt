package com.elbarody.maxabchallange.data.local

import androidx.room.*


@Entity
data class RatesModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 1,

    @ColumnInfo(name = "rates")
    var rates: String? = null
)


