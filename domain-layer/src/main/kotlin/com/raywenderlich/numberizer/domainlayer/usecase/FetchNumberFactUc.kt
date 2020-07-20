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
package com.raywenderlich.numberizer.domainlayer.usecase

import arrow.core.Either
import arrow.core.left
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract.Data.Companion.DATA_REPOSITORY_TAG
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import javax.inject.Inject
import javax.inject.Named

const val FETCH_NUMBER_FACT_UC_TAG = "fetchNumberFactUc"

class FetchNumberFactUc @Inject constructor(
    @Named(DATA_REPOSITORY_TAG)
    private val numberDataRepository: @JvmSuppressWildcards DomainlayerContract.Data.DataRepository<NumberFactResponse>
) : DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse> {

    override suspend fun run(params: NumberFactRequest?): Either<Failure, NumberFactResponse> =
        params?.let {
            numberDataRepository.fetchNumberFact(request = params)
        } ?: run {
            Failure.InputParamsError().left()
        }

}