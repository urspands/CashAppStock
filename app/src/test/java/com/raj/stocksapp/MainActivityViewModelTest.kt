package com.raj.stocksapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.stream.MalformedJsonException
import com.raj.stocksapp.api.PortfolioResponse
import com.raj.stocksapp.api.Stock
import com.raj.stocksapp.repository.DataRepository
import com.raj.stocksapp.viewModel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var repository: DataRepository

    @Mock
    private lateinit var portfolioObserver: Observer<List<Stock>>

    @Mock
    private lateinit var genericErrorObserver: Observer<Boolean>

    @Mock
    private lateinit var internetErrorObserver: Observer<Boolean>

    @Mock
    private lateinit var progressBarObserver: Observer<Boolean>

    @Mock
    private lateinit var emptyStateObserver: Observer<Boolean>

    @Test
    fun testEmptyResponse() {
        testCoroutineRule.runBlockingTest {
            `when`(repository.getPortfolioStocks()).thenReturn(PortfolioResponse(emptyList()))
        }
        val viewModel = MainActivityViewModel(repository)
        viewModel.data.observeForever(portfolioObserver)
        viewModel.showEmptyState.observeForever(emptyStateObserver)
        viewModel.showProgress.observeForever(progressBarObserver)
        viewModel.getPortfolioData()
        runBlocking { verify(repository).getPortfolioStocks() }
        verify(progressBarObserver).onChanged(false)
        verify(emptyStateObserver).onChanged(true)
        verify(portfolioObserver).onChanged(emptyList())
        viewModel.data.removeObserver(portfolioObserver)
        viewModel.showEmptyState.removeObserver(emptyStateObserver)
    }

    @Test
    fun testSuccessResponse() {
        val response = getMockResponse()
        testCoroutineRule.runBlockingTest {
            `when`(repository.getPortfolioStocks()).thenReturn(response)
        }
        val viewModel = MainActivityViewModel(repository)
        viewModel.data.observeForever(portfolioObserver)
        viewModel.showEmptyState.observeForever(emptyStateObserver)
        viewModel.showProgress.observeForever(progressBarObserver)
        viewModel.getPortfolioData()
        runBlocking { verify(repository).getPortfolioStocks() }
        verify(progressBarObserver).onChanged(false)
        verify(emptyStateObserver).onChanged(false)
        verify(portfolioObserver).onChanged(response.stocks)
        viewModel.data.removeObserver(portfolioObserver)
        viewModel.showEmptyState.removeObserver(emptyStateObserver)
    }

    @Test
    fun testMalformedJSONResponse() {
        testCoroutineRule.runBlockingTest {
            given(repository.getPortfolioStocks()).willAnswer {
                throw MalformedJsonException("malformed json")
            }
        }
        val viewModel = MainActivityViewModel(repository)
        viewModel.showProgress.observeForever(progressBarObserver)
        viewModel.showEmptyState.observeForever(emptyStateObserver)
        viewModel.showGenericError.observeForever(genericErrorObserver)
        viewModel.getPortfolioData()
        runBlocking { verify(repository).getPortfolioStocks() }
        verify(progressBarObserver).onChanged(false)
        verify(emptyStateObserver).onChanged(true)
        verify(genericErrorObserver).onChanged(true)
        viewModel.showProgress.removeObserver(progressBarObserver)
        viewModel.showEmptyState.removeObserver(emptyStateObserver)
        viewModel.showGenericError.removeObserver(genericErrorObserver)
    }

    @Test
    fun testAnyErrorResponse() {
        testCoroutineRule.runBlockingTest {
            given(repository.getPortfolioStocks()).willAnswer {
                throw RuntimeException("error")
            }
        }
        val viewModel = MainActivityViewModel(repository)
        viewModel.showProgress.observeForever(progressBarObserver)
        viewModel.showEmptyState.observeForever(emptyStateObserver)
        viewModel.showGenericError.observeForever(genericErrorObserver)
        viewModel.getPortfolioData()
        runBlocking { verify(repository).getPortfolioStocks() }
        verify(progressBarObserver).onChanged(false)
        verify(emptyStateObserver).onChanged(true)
        verify(genericErrorObserver).onChanged(true)
        viewModel.showProgress.removeObserver(progressBarObserver)
        viewModel.showEmptyState.removeObserver(emptyStateObserver)
        viewModel.showGenericError.removeObserver(genericErrorObserver)
    }

    @Test
    fun testNoInternetException() {
        testCoroutineRule.runBlockingTest {
            given(repository.getPortfolioStocks()).willAnswer {
                throw IOException("No internet")
            }
        }
        val viewModel = MainActivityViewModel(repository)
        viewModel.showProgress.observeForever(progressBarObserver)
        viewModel.showEmptyState.observeForever(emptyStateObserver)
        viewModel.showInternetError.observeForever(internetErrorObserver)
        viewModel.getPortfolioData()

        runBlocking { verify(repository).getPortfolioStocks() }
        verify(progressBarObserver).onChanged(false)
        verify(emptyStateObserver).onChanged(true)
        verify(internetErrorObserver).onChanged(true)
        viewModel.showProgress.removeObserver(progressBarObserver)
        viewModel.showEmptyState.removeObserver(emptyStateObserver)
        viewModel.showInternetError.removeObserver(internetErrorObserver)
    }

    private fun getMockResponse(): PortfolioResponse {
        val stocks = listOf(
            Stock("USD", 876, 87632, "Pandiarajan", 10, "RAJ"),
            Stock("USD", 8746, 827632, "Subramani", 130, "SUB"),
        )
        return PortfolioResponse(stocks)
    }
}