package com.elbarody.maxabchallange.presenter.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elbarody.maxabchallange.data.extention.toJson
import com.elbarody.maxabchallange.data.local.RatesModel
import com.elbarody.maxabchallange.data.remote.ViewState
import com.elbarody.maxabchallange.data.repo.currency_data.CurrencyDataRepository
import com.elbarody.maxabchallange.data.repo.database_repo.DatabaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private val currencyDataRepository: CurrencyDataRepository,
    private val repo: DatabaseRepo
) : ViewModel() {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    private val _getCurrencyDataStateFlow =
        MutableLiveData<ViewState<Any>>()
    val getCurrencyDataStateFlow: MutableLiveData<ViewState<Any>>
        get() = _getCurrencyDataStateFlow

    private val _localDataStateFlow =
        MutableLiveData<String>()
    val getLocalCurrencyDataStateFlow: MutableLiveData<String>
        get() = _localDataStateFlow

    fun getCurrencyData(date: String) {
        scope.launch {
            _getCurrencyDataStateFlow.postValue(ViewState.Loading(true))
            currencyDataRepository.getCurrencyData(date)
                .catch {
                    _getCurrencyDataStateFlow.postValue(ViewState.Loading(false))
                    val ratesModel = repo.getCurrencyFromDataBase()
                    if (ratesModel != null)
                    _localDataStateFlow.postValue(ratesModel.rates!!)


                    it.message?.let {
                        _getCurrencyDataStateFlow.postValue(
                            ViewState.GeneralError(0, it)
                        )
                    }

                }.buffer()
                .collect {
                    _getCurrencyDataStateFlow.postValue(ViewState.Loading(false))
                    if (it.isSuccessful) {
                        repo.addCurrencyToDataBase(RatesModel(rates = it.body()?.rates?.toString()!!))
                        _getCurrencyDataStateFlow.postValue(ViewState.Success(it))
                    } else
                        _getCurrencyDataStateFlow.postValue(
                            ViewState.DataError(
                                it.code(),
                                it.errorBody()?.string()!!
                            )
                        )
                }
        }
    }
}