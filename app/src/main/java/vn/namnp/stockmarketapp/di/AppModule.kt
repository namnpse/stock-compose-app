package vn.namnp.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import vn.namnp.stockmarketapp.data.local.StockMarketDatabase
import vn.namnp.stockmarketapp.data.remote.StockMarketApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockMarketApi {
        return Retrofit.Builder()
            .baseUrl(StockMarketApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                ).build()
            )
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockMarketDatabase {
        return Room.databaseBuilder(
            app,
            StockMarketDatabase::class.java,
            "stock_market.db"
        ).build()
    }
}