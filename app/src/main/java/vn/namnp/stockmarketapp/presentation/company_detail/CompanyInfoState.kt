package vn.namnp.stockmarketapp.presentation.company_detail

import vn.namnp.stockmarketapp.domain.model.CompanyInfo
import vn.namnp.stockmarketapp.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfo: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
