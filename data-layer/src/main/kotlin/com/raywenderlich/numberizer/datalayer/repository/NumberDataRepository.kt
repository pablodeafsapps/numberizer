package com.raywenderlich.numberizer.datalayer.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.raywenderlich.numberizer.datalayer.datasource.NumberFactDataSource
import com.raywenderlich.numberizer.datalayer.datasource.NumbersApiDataSource
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import java.net.SocketTimeoutException

object NumberDataRepository : DomainlayerContract.Data.DataRepository<NumberFactResponse> {

    private val numberFactDataSource: NumberFactDataSource by lazy { NumbersApiDataSource() }

    @Throws(SocketTimeoutException::class)
    override suspend fun fetchNumberFact(request: NumberFactRequest): Either<Failure, NumberFactResponse> =
        try {
            val response = numberFactDataSource.fetchNumberFact(request = request)

            val body = response.body()
            body.takeIf { response.isSuccessful && it != null }
                ?.right() ?: run { Failure.NoData().left() }
        } catch (e: Exception) {
            Failure.ServerError(e.localizedMessage ?: "Server error").left()
        }

}