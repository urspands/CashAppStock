package com.raj.stocksapp.repository

import com.raj.stocksapp.api.PortfolioResponse

interface DataRepository {

    suspend fun getPortfolioStocks(): PortfolioResponse
}