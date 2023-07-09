package vn.namnp.stockmarketapp.domain.usecase

import vn.namnp.stockmarketapp.domain.model.CompanyInfo
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import javax.inject.Inject

class GetDetailedCompanyStockUseCase @Inject constructor(
    private val repository: StockMarketRepository
) {
    suspend operator fun invoke(
        symbol: String
    ): Resource<CompanyInfo> {
        return repository.getCompanyInfo(symbol)
    }
}