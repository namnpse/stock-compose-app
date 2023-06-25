package vn.namnp.stockmarketapp.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.namnp.stockmarketapp.data.mapper.toIntraDayInfo
import vn.namnp.stockmarketapp.data.remote.dto.IntraDayInfoDto
import vn.namnp.stockmarketapp.domain.model.IntraDayInfo
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayInfoParser @Inject constructor(): CSVParser<IntraDayInfo> {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntraDayInfoDto(timestamp, close.toDouble())
                    dto.toIntraDayInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}