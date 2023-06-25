package vn.namnp.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockMarketApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getCompanyListing(
        @Query("apiKey") apiKey: String = API_KEY,
    ): ResponseBody

    companion object {
        const val API_KEY = "Q63Y9NX3TUF587NF"
        const val BASE_URL = "https://alphavantage.co"
    }
}