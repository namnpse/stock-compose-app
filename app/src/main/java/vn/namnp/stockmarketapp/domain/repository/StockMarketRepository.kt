package vn.namnp.stockmarketapp.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.namnp.stockmarketapp.domain.model.CompanyListing
import vn.namnp.stockmarketapp.util.Resource

interface StockMarketRepository {

    fun getCompanyListing(
        getRemoteData: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>
}