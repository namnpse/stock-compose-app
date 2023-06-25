package vn.namnp.stockmarketapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import vn.namnp.stockmarketapp.csv.CSVParser
import vn.namnp.stockmarketapp.data.local.StockMarketDao
import vn.namnp.stockmarketapp.data.local.StockMarketDatabase
import vn.namnp.stockmarketapp.data.mapper.toCompanyListing
import vn.namnp.stockmarketapp.data.mapper.toCompanyListingEntity
import vn.namnp.stockmarketapp.data.remote.StockMarketApi
import vn.namnp.stockmarketapp.domain.model.CompanyListing
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import java.io.IOException

class StockMarketRepositoryImpl(
    private val api: StockMarketApi,
    private val db: StockMarketDatabase,
    private val csvParser: CSVParser<CompanyListing>,
) : StockMarketRepository {

    private val dao: StockMarketDao = db.dao

    override fun getCompanyListing(
        getRemoteData: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow<Resource<List<CompanyListing>>> {
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
                csvParser.parse(response.byteStream())
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Couldn't load data"))
                null
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Couldn't load data"))
                null
            }

            remoteCompanyListing?.let { companyListings ->
//                emit(Resource.Success(
//                    data = companyListings
//                ))
//                dao.clearCompanyListings()
//                dao.insertCompanyListings(companyListings.map { it.toCompanyListingEntity() })
//                emit(Resource.Loading(false))
//                -> NOT follow Single source of truth -> cause emit remote data immediately -> BAD

//                GOOD: get remote data from api -> save to local -> emit local data
                dao.clearCompanyListings()
                dao.insertCompanyListings(companyListings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(
                    data = companyListings
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}