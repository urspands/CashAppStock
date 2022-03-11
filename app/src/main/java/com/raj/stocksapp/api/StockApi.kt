package com.raj.stocksapp.api

import retrofit2.http.GET

/**
 * Stock api
 * provides APIs to get Portfolio data from server
 * @constructor Create empty Stock api
 */
interface StockApi {
    companion object {
        const val BASE_URL = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"
        private const val PORTFOLIO = "portfolio.json"
        private const val PORTFOLIO_MALFORMED = "portfolio_malformed.json"
        private const val PORTFOLIO_EMPTY_RESPONSE = "portfolio_empty.json"
    }

    @GET(PORTFOLIO)
    suspend fun getPortfolioStocks(): PortfolioResponse

    @GET(PORTFOLIO_MALFORMED)
    suspend fun getPortfolioStocksMalformed(): PortfolioResponse

    @GET(PORTFOLIO_EMPTY_RESPONSE)
    suspend fun getPortfolioEmptyStocks(): PortfolioResponse
}