package com.tymeglobal.test.currency_converter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tymeglobal.test.currency_converter.R
import com.tymeglobal.test.currency_converter.common.interfaces.ItemClickListener
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.databinding.ItemCurrencySymbolsBinding
import com.tymeglobal.test.currency_converter.ui.base.BaseAdapter
import com.tymeglobal.test.currency_converter.ui.base.BaseDiffUtil
import com.tymeglobal.test.currency_converter.ui.viewholders.CurrencyViewHolder

class CurrencyAdapter(
    private var listener: ItemClickListener
) : BaseAdapter<CurrencyInfoEntity>(
    BaseDiffUtil(
        areItemsTheSameCustom = { oldItem, newItem -> oldItem.currencyCode == newItem.currencyCode },
        areContentsTheSameCustom = { oldItem, newItem -> oldItem == newItem }
    )
) {

    private var selectedItem: CurrencyInfoEntity = CurrencyInfoEntity()

    fun setSelectedItem(data: CurrencyInfoEntity) {
        selectedItem = data
    }

    override fun createCustomViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemCurrencySymbolsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_currency_symbols,
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun bindCustomViewHolder(
        holder: RecyclerView.ViewHolder,
        item: CurrencyInfoEntity,
        position: Int
    ) {
        (holder as CurrencyViewHolder).bind(item, selectedItem, listener)
    }
}