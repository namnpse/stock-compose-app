package vn.namnp.stockmarketapp.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.namnp.stockmarketapp.domain.model.CompanyInfo
import vn.namnp.stockmarketapp.domain.model.CompanyListing
import vn.namnp.stockmarketapp.domain.model.IntraDayInfo
import vn.namnp.stockmarketapp.util.Resource

interface StockMarketRepository {

    fun getCompanyListing(
        getRemoteData: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}