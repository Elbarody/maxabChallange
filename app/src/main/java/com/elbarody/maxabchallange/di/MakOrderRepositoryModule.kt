package com.elbarody.maxabchallange.di


import com.elbarody.maxabchallange.data.repo.currency_data.CurrencyDataRepository
import com.elbarody.maxabchallange.data.repo.currency_data.CurrencyDataRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MakOrderRepositoryModule {
    @Binds
    abstract fun providesCurrencyDataRepo(currencyDataRepositoryImp: CurrencyDataRepositoryImp): CurrencyDataRepository

}