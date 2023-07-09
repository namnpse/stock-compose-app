package vn.namnp.stockmarketapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.namnp.stockmarketapp.domain.repository.StockMarketRepository
import vn.namnp.stockmarketapp.domain.usecase.GetDetailedCompanyStockUseCase
import vn.namnp.stockmarketapp.domain.usecase.GetIntraDayInfoUseCase
import vn.namnp.stockmarketapp.domain.usecase.GetListCompanyStockUseCase
import vn.namnp.stockmarketapp.domain.usecase.StockMarketUseCases
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun providesUseCases(repository: StockMarketRepository): StockMarketUseCases {
        return StockMarketUseCases(
            getListCompanyStockUseCase = GetListCompanyStockUseCase(repository),
            getDetailedCompanyStockUseCase = GetDetailedCompanyStockUseCase(repository),
            getIntraDayInfoUseCase = GetIntraDayInfoUseCase(repository),
        )
    }
}