package com.tymeglobal.test.currency_converter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.tymeglobal.test.currency_converter.common.extensions.setOnSingleClickListener
import com.tymeglobal.test.currency_converter.databinding.FragmentExchangeCurrencyBinding
import com.tymeglobal.test.currency_converter.ui.base.BaseFragment
import com.tymeglobal.test.currency_converter.ui.dialogs.CurrencyListBottomSheetDialog
import com.tymeglobal.test.currency_converter.ui.viewmodels.ExchangeCurrencyViewModel
import com.tymeglobal.test.currency_converter.ui.viewmodels.MainViewModel
import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeCurrencyFragment :
    BaseFragment<FragmentExchangeCurrencyBinding, ExchangeCurrencyViewModel>() {
    private val TAG = ExchangeCurrencyFragment::class.java.simpleName

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoggerUtils.i(TAG, "onViewCreated()")

        binding.exchangeCurrencyViewModel = viewModel
        binding.keyboardLayout.exchangeCurrencyViewModel = viewModel

        initAction()
    }

    private fun initAction() {
        binding.llSelectCurrencyInput.setOnSingleClickListener {
            context?.let {
                viewModel.openBottomSheetDialogCurrency(
                    currencyList = mainViewModel.supportCurrencyList,
                    isFromCurrency = true,
                    context = it
                )
            }
        }
        binding.llSelectCurrencyOutput.setOnSingleClickListener {
            context?.let {
                viewModel.openBottomSheetDialogCurrency(
                    currencyList = mainViewModel.supportCurrencyList,
                    isFromCurrency = false,
                    context = it
                )
            }
        }
    }
}