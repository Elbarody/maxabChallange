package com.elbarody.maxabchallange.di

import android.content.Context
import androidx.room.Room
import com.elbarody.maxabchallange.data.local.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        CurrencyDatabase::class.java,
        "currency.db"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: CurrencyDatabase) = db.currencyDao()
}