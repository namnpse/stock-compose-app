package vn.namnp.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class],
    version = 1,
)
abstract class StockMarketDatabase: RoomDatabase() {

    abstract val dao: StockMarketDao
}