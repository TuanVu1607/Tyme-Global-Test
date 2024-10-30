package com.tymeglobal.test.currency_converter.ui.viewholders

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.tymeglobal.test.currency_converter.BR
import com.tymeglobal.test.currency_converter.common.extensions.setOnSingleClickListener
import com.tymeglobal.test.currency_converter.common.interfaces.ItemClickListener
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.databinding.ItemCurrencySymbolsBinding
import com.tymeglobal.test.currency_converter.utils.LoggerUtils

class CurrencyViewHolder(private val binding: ItemCurrencySymbolsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: CurrencyInfoEntity,
        selectedItem: CurrencyInfoEntity,
        itemClickListener: ItemClickListener
    ) {
        binding.setVariable(BR.currencySymbols, data)
        binding.executePendingBindings()
        binding.ivCheck.isVisible = selectedItem.currencyCode == data.currencyCode

        itemView.setOnSingleClickListener {
            if (selectedItem.currencyCode != data.currencyCode)
                itemClickListener.onClick(data)
        }
    }
}