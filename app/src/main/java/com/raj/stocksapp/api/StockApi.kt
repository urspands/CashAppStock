package com.raj.stocksapp.api

import retrofit2.http.GET

interface StockApi {
    companion object {
        const val BASE_URL = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"
        private const val PORTFOLIO = "portfolio.json"
        private const val PORTFOLIO_MALFORMED = "portfolio_malformed.json"
    }

    @GET(PORTFOLIO)
    suspend fun getPortfolioStocks(): PortfolioResponse

    @GET(PORTFOLIO_MALFORMED)
    suspend fun getPortfolioStocksMalformed(): PortfolioResponse
}