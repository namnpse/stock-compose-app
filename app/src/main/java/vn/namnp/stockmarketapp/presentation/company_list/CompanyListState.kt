package vn.namnp.stockmarketapp.presentation.company_list

import vn.namnp.stockmarketapp.domain.model.CompanyListing

data class CompanyListState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
)
