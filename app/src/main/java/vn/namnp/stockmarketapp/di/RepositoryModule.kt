package vn.namnp.stockmarketapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.namnp.stockmarketapp.csv.CSVParser
import vn.namnp.stockmarketapp.csv.StockMarketCSVParser
import vn.namnp.stockmarketapp.data.local.StockMarketDatabase
import vn.namnp.stockmarketapp.data.remote.StockMarketApi
import vn.namnp.stockmarketapp.data.repository.StockMarketRepositoryImpl
import vn.namnp.stockmarketapp.domain.model.CompanyListing
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds // used for interface, abstract functions
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: StockMarketCSVParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockMarketRepositoryImpl
    ): StockMarketRepository

    // use @Bind to avoid boilerplate code like code below -> more concise, more simple like above
//    @Provides
//    @Singleton
//    fun providesStockRepository(
//        api: StockMarketApi,
//        database: StockMarketDatabase,
//        parser: StockMarketCSVParser,
//    ): StockMarketRepository {
//        return StockMarketRepositoryImpl(
//            api = api,
//            db = database,
//            csvParser = parser,
//        )
//    }
}