package com.raj.stocksapp.viewModel

import androidx.lifecycle.ViewModel
import com.raj.stocksapp.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Main activity view model
 *
 * @constructor Create empty Main activity view model
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
}