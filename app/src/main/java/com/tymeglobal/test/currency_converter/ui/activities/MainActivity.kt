package com.tymeglobal.test.currency_converter.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.tymeglobal.test.currency_converter.R
import com.tymeglobal.test.currency_converter.common.process.LoadingDialog
import com.tymeglobal.test.currency_converter.databinding.ActivityMainBinding
import com.tymeglobal.test.currency_converter.ui.base.BaseActivity
import com.tymeglobal.test.currency_converter.ui.viewmodels.MainViewModel
import com.tymeglobal.test.currency_converter.utils.LoggerUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy { findNavController(R.id.nav_host_fragment_content_main) }

    private val mainViewModel by viewModels<MainViewModel>()
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        LoggerUtils.i(TAG, "onCreate()")

        binding.mainViewModel = mainViewModel
        loadingDialog = LoadingDialog(this, lifecycle.coroutineScope, mainViewModel.loader)

        setupAppBarAndNavigation()

        mainViewModel.fetchSupportCurrencyList()
    }

    override fun onPause() {
        super.onPause()
        if (loadingDialog != null && mainViewModel.loader.loading.value) {
            mainViewModel.loader.stop()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupAppBarAndNavigation() {
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}