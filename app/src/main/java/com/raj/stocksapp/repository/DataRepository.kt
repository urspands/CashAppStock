package com.raj.stocksapp.repository

import com.raj.stocksapp.api.PortfolioResponse

/**
 * Data repository
 * APIs to implement in the Data layer
 * @constructor Create empty Data repository
 */
interface DataRepository {
    suspend fun getPortfolioStocks(): PortfolioResponse
}