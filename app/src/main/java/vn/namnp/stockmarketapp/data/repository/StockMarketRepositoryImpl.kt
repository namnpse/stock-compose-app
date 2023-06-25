package vn.namnp.stockmarketapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import vn.namnp.stockmarketapp.data.local.StockMarketDao
import vn.namnp.stockmarketapp.data.local.StockMarketDatabase
import vn.namnp.stockmarketapp.data.mapper.toCompanyListing
import vn.namnp.stockmarketapp.data.remote.StockMarketApi
import vn.namnp.stockmarketapp.domain.model.CompanyListing
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import java.io.IOException

class StockMarketRepositoryImpl(
    private val api: StockMarketApi,
    private val db: StockMarketDatabase,
) : StockMarketRepository {

    private val dao: StockMarketDao = db.dao

    override fun getCompanyListing(
        getRemoteData: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCompanyListing = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localCompanyListing.map { it -> it.toCompanyListing() }
            ))
            val isLocalDataEmpty = query.isEmpty() && localCompanyListing.isEmpty()
            val shouldLoadCachedData = !getRemoteData && !isLocalDataEmpty
            if(shouldLoadCachedData) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteCompanyListing = try {
                val response = api.getCompanyListing(query)
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Couldn't load data"))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Couldn't load data"))
            }
        }
    }
}