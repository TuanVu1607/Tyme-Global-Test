package com.tymeglobal.test.currency_converter.ui.viewholders

import androidx.core.view.isVisible
import com.tymeglobal.test.currency_converter.BR
import com.tymeglobal.test.currency_converter.common.extensions.setOnSingleClickListener
import com.tymeglobal.test.currency_converter.common.interfaces.ItemClickListener
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.databinding.ItemCurrencySymbolsBinding
import com.tymeglobal.test.currency_converter.ui.base.BaseViewHolder

class CurrencyViewHolder(binding: ItemCurrencySymbolsBinding) :
    BaseViewHolder<ItemCurrencySymbolsBinding>(binding) {

    fun bind(
        item: CurrencyInfoEntity,
        selectedItem: CurrencyInfoEntity,
        listener: ItemClickListener
    ) {
        binding.setVariable(BR.currencySymbols, item)
        binding.executePendingBindings()
        binding.ivCheck.isVisible = selectedItem.currencyCode == item.currencyCode

        binding.root.setOnSingleClickListener {
            if (selectedItem.currencyCode != item.currencyCode) listener.onClick(item)
        }
    }
}