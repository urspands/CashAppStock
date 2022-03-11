package com.raj.stocksapp.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
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

    private lateinit var _dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        //Setup recycler View
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
        // observe server data to display in recycler view
        _viewModel.data.observe(this) {
            Log.d(TAG, "onCreate: response size -> ${it.size}")
            _binding.recyclerView.adapter = StockListAdapter(ArrayList(it))
        }
        // observe data to show and hide generic error
        _viewModel.showGenericError.observe(this) {
            showErrorAlertDialog(getString(R.string.generic_error_msg))
        }
        // observe data to show and hide internet error
        _viewModel.showInternetError.observe(this) {
            showErrorAlertDialog(getString(R.string.internet_error_msg))
        }
        // observe data to show and hide progress bar
        _viewModel.showProgress.observe(this) {
            if (it)
                _dialog.show()
            else
                _dialog.dismiss()
        }
        // observe data to show and hide emptyState
        _viewModel.showEmptyState.observe(this) {
            _binding.emptyState.visibility = if (it) View.VISIBLE else View.GONE
            _binding.recyclerView.visibility = if (it) View.GONE else View.VISIBLE
        }
        // get data from the server
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