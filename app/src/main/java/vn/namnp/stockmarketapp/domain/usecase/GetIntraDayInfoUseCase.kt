package vn.namnp.stockmarketapp.domain.usecase

import vn.namnp.stockmarketapp.domain.model.IntraDayInfo
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.util.Resource
import javax.inject.Inject

class GetIntraDayInfoUseCase @Inject constructor(
    private val repository: StockMarketRepository
) {
    suspend operator fun invoke(
        symbol: String
    ): Resource<List<IntraDayInfo>> {
        return repository.getIntraDayInfo(symbol)
    }
}