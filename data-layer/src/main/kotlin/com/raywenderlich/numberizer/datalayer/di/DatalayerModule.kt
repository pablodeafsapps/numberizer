package com.raywenderlich.numberizer.datalayer.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.raywenderlich.numberizer.datalayer.BuildConfig
import com.raywenderlich.numberizer.datalayer.datasource.NumberFactDataSource
import com.raywenderlich.numberizer.datalayer.datasource.NumberFactDataSource.Companion.NUMBER_FACT_DATA_SOURCE_TAG
import com.raywenderlich.numberizer.datalayer.datasource.NumbersApiDataSource
import com.raywenderlich.numberizer.datalayer.repository.NumberDataRepository
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract.Data.Companion.DATA_REPOSITORY_TAG
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class, DatasourceModule::class])
interface DatalayerComponent {

    @Component.Factory
    interface Factory {
        fun create(): DatalayerComponent
    }

    // downstream dependent components need data types to be exposed
//    @Named(DATA_REPOSITORY_TAG)
    fun dataRepository(): DomainlayerContract.Data.DataRepository<NumberFactResponse>

}

@Module
object RepositoryModule {

    @Provides
//    @Named(DATA_REPOSITORY_TAG)
    fun provideDataRepository(
        @Named(NUMBER_FACT_DATA_SOURCE_TAG)
        numberFactDs: NumberFactDataSource
    ): DomainlayerContract.Data.DataRepository<NumberFactResponse> =
        NumberDataRepository.apply { numberFactDataSource = numberFactDs }

}

@Module
class DatasourceModule {

    @Provides
    @Named(NUMBER_FACT_DATA_SOURCE_TAG)
    fun provideNumberFactDataSource(ds: NumbersApiDataSource): NumberFactDataSource = ds

    @Provides
    fun provideRetrofitInstance() = Retrofit.Builder()
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl("${NumberFactDataSource.BASE_URL}/")
        .build()

}

fun getHttpClient(): OkHttpClient {
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