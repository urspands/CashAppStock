package com.raj.stocksapp.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raj.stocksapp.R
import com.raj.stocksapp.adapter.StockListAdapter
import com.raj.stocksapp.databinding.ActivityMainBinding
import com.raj.stocksapp.viewModel.MainActivityViewModel


class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var _binding: ActivityMainBinding
    private val _viewModel: MainActivityViewModel by viewModels()
    private lateinit var _stockListAdapter: StockListAdapter
    private lateinit var _dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        (layoutManager as LinearLayoutManager).orientation
                    )
                )

            }
        }
        initProgressDialog()
        _viewModel.data.observe(this) {
            Log.d(TAG, "onCreate: response size -> ${it.size}")
            if (_binding.recyclerView.adapter == null) {
                _stockListAdapter = StockListAdapter(ArrayList(it))
                _binding.recyclerView.adapter = _stockListAdapter
            } else {
                _stockListAdapter.addStocks(it)
            }
        }
        _viewModel.showGenericError.observe(this) {
            showErrorAlertDialog(getString(R.string.generic_error_msg))
        }
        _viewModel.showInternetError.observe(this) {
            showErrorAlertDialog(getString(R.string.internet_error_msg))
        }
        _viewModel.showProgress.observe(this) {
            if (it)
                _dialog.show()
            else
                _dialog.dismiss()
        }

        _viewModel.getPortfolioData()
    }

    private fun initProgressDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.progress_layout)
        _dialog = builder.create()
    }

    private fun showErrorAlertDialog(errorMsg: String) {
        AlertDialog.Builder(this).setTitle(R.string.error).setMessage(errorMsg)
            .setPositiveButton(
                R.string.okay
            ) { dialog, _ -> dialog.dismiss() }.setCancelable(false).show()
    }
}