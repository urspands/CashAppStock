package com.raj.stocksapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.stream.MalformedJsonException
import com.raj.stocksapp.api.Stock
import com.raj.stocksapp.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
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

    private val _showGenericError: MutableLiveData<Boolean> = MutableLiveData()
    val showGenericError: LiveData<Boolean> get() = _showGenericError
    private val _showInternetError: MutableLiveData<Boolean> = MutableLiveData()
    val showInternetError: LiveData<Boolean> get() = _showInternetError
    private val _showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showProgress: LiveData<Boolean> get() = _showProgress
    private val _showEmptyState: MutableLiveData<Boolean> = MutableLiveData()
    val showEmptyState: LiveData<Boolean> get() = _showEmptyState

    fun getPortfolioData() {
        viewModelScope.launch {
            try {
                _showProgress.value = true
                val response = repository.getPortfolioStocks().stocks
                _data.value = response
                _showEmptyState.value = response.isEmpty()
                _showProgress.value = false
            } catch (t: Throwable) {
                _showEmptyState.value = true
                _showProgress.value = false
                when (t) {
                    is MalformedJsonException -> _showGenericError.value = true
                    is IOException -> _showInternetError.value = true
                    else -> _showGenericError.value = true
                }
            }

        }
    }
}