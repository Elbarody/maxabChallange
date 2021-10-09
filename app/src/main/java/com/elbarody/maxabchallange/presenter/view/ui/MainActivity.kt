package com.elbarody.maxabchallange.presenter.view.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elbarody.maxabchallange.R
import com.elbarody.maxabchallange.data.extention.getMap
import com.elbarody.maxabchallange.data.model.CurrencyModel
import com.elbarody.maxabchallange.data.model.CurrencyResponse
import com.elbarody.maxabchallange.data.remote.ViewState
import com.elbarody.maxabchallange.databinding.ActivityMainBinding
import com.elbarody.maxabchallange.presenter.view.ui.adapter.CurrencyRecyclerViewAdapter
import com.elbarody.maxabchallange.presenter.viewModels.ViewModelMain
import com.elbarody.maxabchallange.utils.DateUtils.getCurrentDate
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Response
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var currencyRecyclerViewAdapter: CurrencyRecyclerViewAdapter
    val viewModel: ViewModelMain by viewModels()
    lateinit var binding: ActivityMainBinding
    var currencyList = ArrayList<CurrencyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUp()
    }

    fun setUp() {
        viewModel.getCurrencyData(getCurrentDate())
        setUpRecyclerView()
        viewModel.getCurrencyDataStateFlow.observe(this, { state ->
            when (state) {
                is ViewState.Loading -> {
                    //showLoading(state.isVisible)
                }
                is ViewState.Success -> {
                    handleCurrencyResponse(state.data as Response<CurrencyResponse>)
                }
                is ViewState.DataError -> {
                    //handleErrorResponse(state.data as String)
                }
                is ViewState.GeneralError -> {
                    //showErrorMessage(state.data.toString())
                }
            }
        })

        viewModel.getLocalCurrencyDataStateFlow.observe(this, {
            if (!it.isNullOrEmpty()) {
                val json = JSONObject(it)
                //json.get("EGP")
                val map = getMap(json as Any)
                addListAndNotifyData((map?.values?.first() as Map<String, Any>))
            }
        })


    }

    private fun setUpRecyclerView() {
        currencyRecyclerViewAdapter = CurrencyRecyclerViewAdapter(currencyList)
        binding.rvCurrencyList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currencyRecyclerViewAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }

    private fun handleCurrencyResponse(response: Response<CurrencyResponse>) {
        if (response.body()?.success!!) {
            val map = getMap(response.body()?.rates)
            addListAndNotifyData(map)
        }
    }

    private fun addListAndNotifyData(map: Map<String, Any>?) {
        map?.forEach {
            currencyList.add(CurrencyModel(it.key, it.value as Double))
            Log.e("Map", "key: ${it.key} - value: ${it.value} \n")
        }
        currencyRecyclerViewAdapter.notifyDataSetChanged()
        searchOutLetSetUp()
    }

    private fun searchOutLetSetUp() {
        val originalList = currencyList.toMutableList()
        binding.etCurrencyAmount.textChanges()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                currencyList.clear()
                currencyList.addAll(originalList as ArrayList<CurrencyModel>)
                if (it.isNotBlank()) {
                    changeAmount(it.toString().toInt())
                } else {

                    changeAmount(1)
                }
            }
    }

    private fun changeAmount(currency: Int) {

        currencyList.forEachIndexed { index, currencyModel ->
            currencyList[index] = CurrencyModel(currencyModel.key, (currencyModel.value * currency))
        }
        currencyRecyclerViewAdapter.notifyDataSetChanged()
    }


}