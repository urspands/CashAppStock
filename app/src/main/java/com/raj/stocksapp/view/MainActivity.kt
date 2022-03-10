package com.raj.stocksapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raj.stocksapp.adapter.StockListAdapter
import com.raj.stocksapp.api.Stock
import com.raj.stocksapp.databinding.ActivityMainBinding
import com.raj.stocksapp.viewModel.MainActivityViewModel

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var _binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var _stockListAdapter: StockListAdapter
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
        viewModel.data.observe(this) {
            Log.d(TAG, "onCreate: response size -> ${it.size}")
            if (_binding.recyclerView.adapter == null) {
                _stockListAdapter = StockListAdapter(ArrayList(it))
                _binding.recyclerView.adapter = _stockListAdapter
            } else {
                _stockListAdapter.addStocks(it)
            }
        }

        viewModel.getPortfolioData()
    }
}