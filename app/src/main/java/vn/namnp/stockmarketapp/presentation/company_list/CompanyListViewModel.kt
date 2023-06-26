package vn.namnp.stockmarketapp.presentation.company_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val stockMarketRepository: StockMarketRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListState())
    private var searchJob: Job? = null

    init {
        getListCompanies()
    }

    fun onEvent(event: CompanyListEvent) {
        when (event) {
            is CompanyListEvent.RefreshData -> {
                getListCompanies(getRemoteData = true)
            }

            is CompanyListEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getListCompanies()
                }
            }
        }
    }

    private fun getListCompanies(
        getRemoteData: Boolean = false,
    ) {
        val query = state.searchQuery.trim().lowercase()
        viewModelScope.launch {
            stockMarketRepository.getCompanyListing(getRemoteData, query)
//                .distinctUntilChanged()
                .collect { result ->
                    when (result) {
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