package vn.namnp.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import vn.namnp.stockmarketapp.data.remote.dto.CompanyInfoDto

interface StockMarketApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getCompanyListing(
        @Query("apikey") apiKey: String = API_KEY,
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntraDayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): CompanyInfoDto

    companion object {
        const val API_KEY = "G1USXWKX272RK4BP"
        const val BASE_URL = "https://alphavantage.co"
    }
}