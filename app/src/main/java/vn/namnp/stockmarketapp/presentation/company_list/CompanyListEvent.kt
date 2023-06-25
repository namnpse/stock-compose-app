package vn.namnp.stockmarketapp.presentation.company_list

sealed class CompanyListEvent {
    object RefreshData: CompanyListEvent()
    data class OnSearchQueryChange(val query: String): CompanyListEvent()
}