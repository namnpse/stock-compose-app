package vn.namnp.stockmarketapp.presentation.company_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource

class CompanyListViewModel(
    private val stockMarketRepository: StockMarketRepository
): ViewModel() {

    var state by mutableStateOf(CompanyListState())
    private var searchJob: Job? = null

    init {
        getListCompany()
    }

    private fun getListCompany(
        getRemoteData: Boolean = false,
    ) {
        val query = state.searchQuery.trim().lowercase()
        viewModelScope.launch {
            stockMarketRepository.getCompanyListing(getRemoteData, query)
                .collect { result ->
                    when(result) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
                            result.data?.let { companies ->
                                state = state.copy(
                                    companies = companies,
                                    isLoading = false,
                                )
                            }
                        }
                        is Resource.Error -> {}
                    }
                }
        }
    }
}