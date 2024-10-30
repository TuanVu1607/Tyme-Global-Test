package com.tymeglobal.test.currency_converter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tymeglobal.test.currency_converter.R
import com.tymeglobal.test.currency_converter.common.interfaces.ItemClickListener
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.databinding.ItemCurrencySymbolsBinding
import com.tymeglobal.test.currency_converter.ui.viewholders.CurrencyViewHolder

class CurrencyAdapter(private var listener: ItemClickListener) :
    RecyclerView.Adapter<CurrencyViewHolder>() {

    private var selectedItem: CurrencyInfoEntity = CurrencyInfoEntity()

    private val differ =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<CurrencyInfoEntity>() {
            override fun areItemsTheSame(
                oldItem: CurrencyInfoEntity,
                newItem: CurrencyInfoEntity
            ): Boolean {
                return oldItem.currencyCode == newItem.currencyCode
            }

            override fun areContentsTheSame(
                oldItem: CurrencyInfoEntity,
                newItem: CurrencyInfoEntity
            ): Boolean {
                return oldItem == newItem
            }
        })

    fun getAdapterData() = differ.currentList

    fun setAdapterData(data: List<CurrencyInfoEntity>) = differ.submitList(data)

    fun setSelectedItem(data: CurrencyInfoEntity) {
        selectedItem = data
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: ItemCurrencySymbolsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_currency_symbols,
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, selectedItem, listener)
    }
}