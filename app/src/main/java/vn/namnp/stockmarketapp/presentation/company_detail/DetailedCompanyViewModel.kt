package vn.namnp.stockmarketapp.presentation.company_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import javax.inject.Inject

@HiltViewModel
class DetailedCompanyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockMarketRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        val symbol = savedStateHandle.get<String>("symbol")
        symbol?.let { symbol ->
            getDetailedCompany(symbol)
        }
    }

    private fun getDetailedCompany(symbol: String) = viewModelScope.launch {
        state = state.copy(isLoading = true)
        val companyInfoResult = async { repository.getCompanyInfo(symbol) }
        val intraDayInfoResult = async { repository.getIntraDayInfo(symbol) }
        when (val result = companyInfoResult.await()) {
            is Resource.Success -> {
                state = state.copy(
                    company = result.data,
                    isLoading = false,
                    errorMessage = null
                )
            }

            is Resource.Error -> {
                state = state.copy(
                    company = null,
                    isLoading = false,
                    errorMessage = result.message,
                )
            }

            else -> Unit
        }
        when (val result = intraDayInfoResult.await()) {
            is Resource.Success -> {
                state = state.copy(
                    stockInfo = result.data ?: emptyList(),
                    errorMessage = null,
                    isLoading = false,
                )
            }

            is Resource.Error -> {
                state = state.copy(
                    company = null,
                    isLoading = false,
                    errorMessage = result.message,
                )
            }

            else -> {}
        }
    }
}