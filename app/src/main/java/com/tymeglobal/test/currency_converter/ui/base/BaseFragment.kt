package com.tymeglobal.test.currency_converter.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    companion object{
        const val TAG = "BaseFragment"
    }

    protected lateinit var binding: VB private set
    protected lateinit var viewModel: VM private set

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LoggerUtils.i(TAG, "onCreateView()")
        binding = inflateMethod.invoke(null, inflater, container, false) as VB
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value

        return binding.root
    }
}