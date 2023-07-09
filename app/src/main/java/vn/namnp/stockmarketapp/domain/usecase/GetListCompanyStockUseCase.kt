package vn.namnp.stockmarketapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.namnp.stockmarketapp.domain.model.CompanyListing
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import javax.inject.Inject

class GetListCompanyStockUseCase @Inject constructor(
    private val repository: StockMarketRepository
) {
    operator fun invoke(
        getRemoteData: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return repository.getCompanyListing(getRemoteData, query)
    }
}