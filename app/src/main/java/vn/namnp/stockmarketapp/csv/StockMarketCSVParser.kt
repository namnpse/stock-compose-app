package vn.namnp.stockmarketapp.csv

import vn.namnp.stockmarketapp.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

class StockMarketCSVParser: CSVParser<CompanyListing> {

    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)// drop `STT` column
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}