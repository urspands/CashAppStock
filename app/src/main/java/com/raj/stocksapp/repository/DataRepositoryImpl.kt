package com.raj.stocksapp.repository

import com.raj.stocksapp.api.PortfolioResponse
import com.raj.stocksapp.api.StockApi

/**
 * Data repository impl
 * To make api calls use API provided in this class
 *
 * @property stockApi
 * @constructor Create empty Data repository impl
 */
class DataRepositoryImpl(private val stockApi: StockApi) : DataRepository {
    override suspend fun getPortfolioStocks(): PortfolioResponse {
        //Choose which API to call
        return stockApi.getPortfolioStocks()
//        return stockApi.getPortfolioStocksMalformed()
//        return stockApi.getPortfolioEmptyStocks()
    }


}