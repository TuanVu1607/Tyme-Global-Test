package com.tymeglobal.test.currency_converter.common.process

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.tymeglobal.test.currency_converter.databinding.LoadingDialogBinding
import com.tymeglobal.test.currency_converter.ui.base.BaseDialog
import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoadingDialog(
    context: Context,
    private val lifeCycleScope: LifecycleCoroutineScope,
    private val loader: Loader
) : BaseDialog<LoadingDialogBinding>(
    context,
    LoadingDialogBinding::inflate,
    false
) {
    private val TAG = LoadingDialog::class.java.simpleName

    init {
        LoggerUtils.i(TAG, "initDialog()")
        listenLoader()
    }

    private fun listenLoader() = lifeCycleScope.launch {
        loader.loading.collectLatest { loading ->
            if (loading) {
                if (!isShowing) show()
            } else {
                if (isShowing) dismiss()
            }
        }
    }
}