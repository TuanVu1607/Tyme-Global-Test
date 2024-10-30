package com.tymeglobal.test.currency_converter.ui.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.tymeglobal.test.currency_converter.common.process.Loader
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.domain.repository.MainRepository
import com.tymeglobal.test.currency_converter.ui.base.BaseViewModel
import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val loader: Loader,
    private val mainRepository: MainRepository
) : BaseViewModel(context, loader) {

    private val TAG = MainViewModel::class.java.simpleName

    private val _supportCurrencyList = mutableListOf<CurrencyInfoEntity>()
    val supportCurrencyList: List<CurrencyInfoEntity> = _supportCurrencyList

    init {
        LoggerUtils.i(TAG, "initViewModel()")
        listenSupportCurrencyList()
    }

    private fun listenSupportCurrencyList() = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.getSupportCurrencyListFromDB().collectLatest {
            _supportCurrencyList.addAll(it)
        }
    }

    fun fetchSupportCurrencyList() = viewModelScope.launch {
        launchNetwork {
            mainRepository.getSupportCurrencyList()
                .collectLatest { response ->
                    val currencies = response.supportedCurrenciesMap.values.toList()
                    if (currencies.isEmpty()) {
                        return@collectLatest
                    }

                    mainRepository.deleteAllSupportCurrencyList()
                    mainRepository.saveSupportCurrencyList(
                        currencies.map {
                            CurrencyInfoEntity(
                                it.currencyCode,
                                it.currencyName ?: "",
                                it.countryCode ?: "",
                                it.countryName ?: "",
                                it.icon ?: ""
                            )
                        }
                    )
                    fetchCurrencyLatestRates()
                }
        }
    }

    private fun fetchCurrencyLatestRates() = viewModelScope.launch {
        launchNetwork {
            mainRepository.getCurrencyLatestRates(base = "USD")
                .collectLatest { response ->
                    if (response.rates.isEmpty()) {
                        return@collectLatest
                    }

                    val currencyRates = response.rates.map {
                        CurrencyRatesEntity(
                            it.key,
                            it.value.toDouble()
                        )
                    }
                    mainRepository.deleteAllCurrencyLatestRates()
                    mainRepository.saveCurrencyLatestRates(currencyRates)
                }
        }
    }
}