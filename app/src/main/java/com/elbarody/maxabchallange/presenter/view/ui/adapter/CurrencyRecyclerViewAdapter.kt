package com.elbarody.maxabchallange.presenter.view.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elbarody.maxabchallange.R
import com.elbarody.maxabchallange.data.model.CurrencyModel
import com.elbarody.maxabchallange.databinding.CurrencyItemBinding


class CurrencyRecyclerViewAdapter(val list : MutableList<CurrencyModel>) :
    RecyclerView.Adapter<CurrencyRecyclerViewAdapter.MainMenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MainMenuViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)
    )



    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.onBind(list[position])

    }

    inner class MainMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: CurrencyModel) = with(CurrencyItemBinding.bind(itemView)) {
            tvCurrancyName.text = item.key
            tvCurrancyValue.text = item.value.toString()
        }
    }
}