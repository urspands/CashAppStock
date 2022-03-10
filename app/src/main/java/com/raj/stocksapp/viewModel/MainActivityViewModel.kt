package com.raj.stocksapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raj.stocksapp.api.Stock
import com.raj.stocksapp.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity view model
 *
 * @constructor Create empty Main activity view model
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private val _data: MutableLiveData<List<Stock>> = MutableLiveData()
    val data: LiveData<List<Stock>> get() = _data


    fun getPortfolioData() {
        viewModelScope.launch {
            val response = repository.getPortfolioStocks()
            _data.value = response.stocks
        }
    }
}