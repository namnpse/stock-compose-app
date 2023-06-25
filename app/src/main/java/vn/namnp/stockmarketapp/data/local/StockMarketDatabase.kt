package vn.namnp.stockmarketapp.data.local

import androidx.room.Database

@Database(
    entities = [CompanyListingEntity::class],
    version = 1,
)
abstract class StockMarketDatabase {

    abstract val dao: StockMarketDao
}