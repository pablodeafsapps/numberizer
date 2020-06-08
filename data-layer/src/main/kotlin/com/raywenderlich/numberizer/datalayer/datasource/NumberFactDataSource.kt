package com.raywenderlich.numberizer.datalayer.datasource

import arrow.core.Either
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.raywenderlich.numberizer.datalayer.BuildConfig
import com.raywenderlich.numberizer.datalayer.datasource.NumberFactDataSource.Companion.BASE_URL
import com.raywenderlich.numberizer.datalayer.service.NumbersApiService
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface NumberFactDataSource {

    companion object {
        const val BASE_URL = "http//numbersapi.com"
        const val NUMBER_FACT_DATA_SOURCE_TAG = "numberFactDataSource"
    }

    suspend fun fetchNumberFact(request: NumberFactRequest): Response<NumberFactResponse>

}

class NumbersApiDataSource : NumberFactDataSource {

    private val retrofit: Retrofit by lazy { getRetrofitInstance() }

    override suspend fun fetchNumberFact(request: NumberFactRequest): Response<NumberFactResponse> =
        retrofit.create(NumbersApiService::class.java).getNumberFactAsync(number = request.number.toString())

}

private fun getRetrofitInstance() = Retrofit.Builder()
    .client(provideHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl("$BASE_URL/")
    .build()

fun provideHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
}
