package com.tymeglobal.test.currency_converter.ui.dialogs

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tymeglobal.test.currency_converter.common.extensions.lowercase
import com.tymeglobal.test.currency_converter.common.extensions.removeNonAlphanumeric
import com.tymeglobal.test.currency_converter.common.interfaces.ItemClickListener
import com.tymeglobal.test.currency_converter.common.interfaces.OnCurrencySelectListener
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.databinding.CurrencyListBottomSheetDialogBinding
import com.tymeglobal.test.currency_converter.ui.adapters.CurrencyAdapter

class CurrencyListBottomSheetDialog(
    context: Context,
    private val currencyList: List<CurrencyInfoEntity>,
    private val selectedCurrency: CurrencyInfoEntity,
    private val isFromCurrency: Boolean,
    private val onCurrencySelectListener: OnCurrencySelectListener
) : BottomSheetDialog(context), ItemClickListener {
    private val binding = CurrencyListBottomSheetDialogBinding.inflate(layoutInflater)
    private lateinit var adapter: CurrencyAdapter

    private var keySearch = ""
    private var searchCurrencyList = currencyList

    init {
        behavior.maxHeight = Resources.getSystem().displayMetrics.heightPixels * 2 / 3
        behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels * 2 / 3
        setContentView(binding.root)

        initAdapter()

        binding.svSearchCurrencySymbols.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    keySearch = it
                    val searchText = keySearch.lowercase().removeNonAlphanumeric()
                    searchCurrencyList = if (searchText.isEmpty())
                        currencyList
                    else
                        currencyList.filter { currency ->
                            currency.currencyCode.lowercase().removeNonAlphanumeric()
                                .contains(searchText)
                                    || currency.currencyName.lowercase().removeNonAlphanumeric()
                                .contains(searchText)
                                    || currency.countryCode.lowercase().removeNonAlphanumeric()
                                .contains(searchText)
                                    || currency.countryName.lowercase().removeNonAlphanumeric()
                                .contains(searchText)
                        }
                    adapter.setAdapterData(searchCurrencyList)
                    setVisibleData()
                }
                return false
            }
        })
    }

    private fun initAdapter() {
        adapter = CurrencyAdapter(this)
        adapter.setSelectedItem(selectedCurrency)
        adapter.setAdapterData(searchCurrencyList)
        binding.rcvCurrencySymbols.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rcvCurrencySymbols.addItemDecoration(
            DividerItemDecoration(
                binding.rcvCurrencySymbols.context,
                RecyclerView.VERTICAL
            )
        )
        binding.rcvCurrencySymbols.adapter = adapter
        setVisibleData()
    }

    private fun setVisibleData() {
        binding.rcvCurrencySymbols.isVisible = searchCurrencyList.isNotEmpty()
        binding.layoutEmpty.root.isVisible = searchCurrencyList.isEmpty()
    }

    override fun onClick(objects: Any?) {
        val currencyInfoEntity = objects as CurrencyInfoEntity
        onCurrencySelectListener.onCurrencySelected(currencyInfoEntity, isFromCurrency)
        dismiss()
    }
}