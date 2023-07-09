package vn.namnp.stockmarketapp.domain.usecase

data class StockMarketUseCases(
    val getListCompanyStockUseCase: GetListCompanyStockUseCase,
    val getDetailedCompanyStockUseCase: GetDetailedCompanyStockUseCase,
    val getIntraDayInfoUseCase: GetIntraDayInfoUseCase,
)