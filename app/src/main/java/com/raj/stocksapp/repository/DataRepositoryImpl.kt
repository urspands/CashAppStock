package com.raj.stocksapp.repository

import com.raj.stocksapp.api.PortfolioResponse
import com.raj.stocksapp.api.StockApi

class DataRepositoryImpl(private val stockApi: StockApi) : DataRepository {
    override suspend fun getPortfolioStocks(): PortfolioResponse {
        TODO("Not yet implemented")
    }


}