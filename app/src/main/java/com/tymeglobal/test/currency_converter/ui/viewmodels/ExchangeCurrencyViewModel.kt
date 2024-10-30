package com.tymeglobal.test.currency_converter.ui.viewmodels

import android.content.Context
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.tymeglobal.test.currency_converter.R
import com.tymeglobal.test.currency_converter.common.extensions.convertMoneyStringToBigDecimal
import com.tymeglobal.test.currency_converter.common.extensions.convertToMoneyString
import com.tymeglobal.test.currency_converter.common.extensions.toast
import com.tymeglobal.test.currency_converter.common.interfaces.OnCurrencySelectListener
import com.tymeglobal.test.currency_converter.common.process.Loader
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyInfoEntity
import com.tymeglobal.test.currency_converter.data.local.db.entity.CurrencyRatesEntity
import com.tymeglobal.test.currency_converter.data.model.CurrencyInfoRates
import com.tymeglobal.test.currency_converter.domain.repository.ExchangeCurrencyRepository
import com.tymeglobal.test.currency_converter.ui.base.BaseViewModel
import com.tymeglobal.test.currency_converter.ui.dialogs.CurrencyListBottomSheetDialog
import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeCurrencyViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loader: Loader,
    private val exchangeCurrencyRepository: ExchangeCurrencyRepository
) : BaseViewModel(context, loader), OnCurrencySelectListener {

    val inputAmount = ObservableField("0")
    val inputCurrency = ObservableField(CurrencyInfoEntity())
    val outputAmount = ObservableField("0")
    val outputCurrency = ObservableField(CurrencyInfoEntity())

    private val _currencyLatestRates = mutableListOf<CurrencyRatesEntity>()
    val currencyLatestRates: List<CurrencyRatesEntity> = _currencyLatestRates

    init {
        LoggerUtils.i(TAG, "initViewModel()")
        getFromToCurrency()
        listenCurrencyLatestRates()
    }

    private fun getFromToCurrency() = viewModelScope.launch {
        val fromCurrency = exchangeCurrencyRepository.getSelectedFromCurrency()
        val toCurrency = exchangeCurrencyRepository.getSelectedToCurrency()
        inputAmount.set(fromCurrency.amount)
        inputCurrency.set(
            CurrencyInfoEntity(
                fromCurrency.currencyCode,
                fromCurrency.currencyName,
                fromCurrency.countryCode,
                fromCurrency.countryName,
                fromCurrency.icon
            )
        )
        outputAmount.set(toCurrency.amount)
        outputCurrency.set(
            CurrencyInfoEntity(
                toCurrency.currencyCode,
                toCurrency.currencyName,
                toCurrency.countryCode,
                toCurrency.countryName,
                toCurrency.icon
            )
        )
    }

    private fun listenCurrencyLatestRates() = viewModelScope.launch(Dispatchers.IO) {
        exchangeCurrencyRepository.getCurrencyLatestRatesFromDB().collectLatest {
            _currencyLatestRates.addAll(it)
            applyListenChangeConvert()
        }
    }

    private fun applyListenChangeConvert() {
        inputAmount.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                saveSelectedCurrency(inputCurrency.get()!!, true)
                convertCurrency().cancel()
                convertCurrency()
            }
        })
        inputCurrency.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                convertCurrency().cancel()
                convertCurrency()
            }
        })
        outputCurrency.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                convertCurrency().cancel()
                convertCurrency()
            }
        })
    }

    private fun convertCurrency() = viewModelScope.launch {
        delay(500)
        val inputAmountValue = inputAmount.get()!!
        val inputCurrencyValue = inputCurrency.get()!!
        val outputCurrencyValue = outputCurrency.get()!!
        if (inputAmountValue == "0") {
            outputAmount.set("0")
            saveSelectedCurrency(outputCurrencyValue, false)
            return@launch
        }
        if (inputCurrencyValue.currencyCode.isEmpty()) {
            context.toast("Vui lòng chọn loại tiền tệ cần chuyển đổi")
            return@launch
        }
        if (outputCurrencyValue.currencyCode.isEmpty()) {
            context.toast("Vui lòng chọn loại tiền tệ muốn chuyển đổi")
            return@launch
        }
        if (inputCurrencyValue.currencyCode == outputCurrencyValue.currencyCode) {
            outputAmount.set(inputAmountValue)
            saveSelectedCurrency(outputCurrencyValue, false)
            return@launch
        }
        val fromCurrencyRates =
            currencyLatestRates.find { it.currencyCode == inputCurrencyValue.currencyCode }
        val toCurrencyRates =
            currencyLatestRates.find { it.currencyCode == outputCurrencyValue.currencyCode }
        if (fromCurrencyRates == null) {
            context.toast("Loại tiền tệ cần chuyển đổi không khả dụng. Vui lòng chọn loại khác")
            return@launch
        }
        if (toCurrencyRates == null) {
            context.toast("Loại tiền tệ muốn chuyển đổi không khả dụng. Vui lòng chọn loại khác")
            return@launch
        }
        val outputAmountBigDecimal =
            inputAmountValue.convertMoneyStringToBigDecimal() * toCurrencyRates.rate.toBigDecimal() / fromCurrencyRates.rate.toBigDecimal()
        outputAmount.set(outputAmountBigDecimal.convertToMoneyString())
        saveSelectedCurrency(outputCurrencyValue, false)
    }

    fun setInputAmount(textKeyboard: String) {
        when (textKeyboard) {
            context.getString(R.string.all_clear_button) -> inputAmount.set("0")

            context.getString(R.string.backspace_button) -> inputAmount.set(
                if (inputAmount.get()!!.substring(0, inputAmount.get()!!.length - 1).isNotEmpty())
                    inputAmount.get()!!.substring(0, inputAmount.get()!!.length - 1)
                        .convertMoneyStringToBigDecimal().convertToMoneyString()
                else "0"
            )

            context.getString(R.string.comma_symbol) -> {
                val inputAmountValue = inputAmount.get()!!
                if (!inputAmountValue.contains(context.getString(R.string.comma_symbol)))
                    inputAmount.set(inputAmountValue + textKeyboard)
            }

            else -> {
                val inputAmountValue = inputAmount.get()!!
                if (inputAmountValue == "0") {
                    inputAmount.set(textKeyboard)
                    return
                }

                val commaIndex = inputAmountValue.indexOf(context.getString(R.string.comma_symbol))
                if (commaIndex != -1 && inputAmountValue.length - commaIndex - 1 == 4) {// cho phép nhập max 4 số sau dấu phẩy
                    return
                }

                inputAmount.set(
                    (inputAmountValue + textKeyboard).convertMoneyStringToBigDecimal()
                        .convertToMoneyString()
                )
            }
        }
    }

    fun openBottomSheetDialogCurrency(
        currencyList: List<CurrencyInfoEntity>,
        isFromCurrency: Boolean,
        context: Context
    ) {
        CurrencyListBottomSheetDialog(
            context,
            currencyList,
            if (isFromCurrency) inputCurrency.get()!! else outputCurrency.get()!!,
            isFromCurrency,
            this
        ).show()
    }

    override fun onCurrencySelected(currency: CurrencyInfoEntity, isFromCurrency: Boolean) {
        (if (isFromCurrency) inputCurrency else outputCurrency).set(currency)
        saveSelectedCurrency(
            currency,
            isFromCurrency,
        )

    }

    private fun saveSelectedCurrency(currency: CurrencyInfoEntity, isFromCurrency: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            val amount = if (isFromCurrency) inputAmount.get()!! else outputAmount.get()!!
            val convertedCurrency = convertDataCurrencyInfoRates(currency, amount)
            if (isFromCurrency)
                exchangeCurrencyRepository.setSelectedFromCurrency(convertedCurrency)
            else
                exchangeCurrencyRepository.setSelectedToCurrency(convertedCurrency)
        }

    private fun convertDataCurrencyInfoRates(currency: CurrencyInfoEntity, amount: String) =
        CurrencyInfoRates(
            currencyCode = currency.currencyCode,
            currencyName = currency.currencyName,
            countryCode = currency.countryCode,
            countryName = currency.countryName,
            icon = currency.icon,
            amount = amount
        )
}