/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
            body.takeIf { response.isSuccessful && it != null }?.let { f ->
                NumberFactResponse(fact = f)
            } ?.right() ?: run { Failure.NoData().left() }
        } catch (e: Exception) {
            Failure.ServerError(e.localizedMessage ?: "Server error").left()
        }

}