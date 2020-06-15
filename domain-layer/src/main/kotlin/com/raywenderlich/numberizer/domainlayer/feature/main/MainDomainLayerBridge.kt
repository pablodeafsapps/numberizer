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
package com.raywenderlich.numberizer.domainlayer.feature.main

import arrow.core.Either
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.domainlayer.usecase.FetchNumberFactUc
import kotlinx.coroutines.CoroutineScope

interface MainDomainLayerBridge {

    fun fetchNumberFact(
        scope: CoroutineScope,
        params: NumberFactRequest,
        onResult: (Either<Failure, NumberFactResponse>) -> Unit = {}
    )

}

class MainDomainLayerBridgeImpl : MainDomainLayerBridge {

    private val fetchNumberFactUc: DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse>
            by lazy { FetchNumberFactUc() }

    override fun fetchNumberFact(
        scope: CoroutineScope,
        params: NumberFactRequest,
        onResult: (Either<Failure, NumberFactResponse>) -> Unit
    ) {
        fetchNumberFactUc.invoke(scope = scope, params = params, onResult = onResult)
    }

}