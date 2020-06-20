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
package com.raywenderlich.numberizer.datalayer.datasource

import com.raywenderlich.numberizer.datalayer.service.NumbersApiService
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

interface NumberFactDataSource {

    companion object {
        const val BASE_URL = "http//numbersapi.com"
        const val NUMBER_FACT_DATA_SOURCE_TAG = "numberFactDataSource"
    }

    suspend fun fetchNumberFact(request: NumberFactRequest): Response<NumberFactResponse>

}

class NumbersApiDataSource @Inject constructor(private val retrofit: Retrofit) :
    NumberFactDataSource {

    override suspend fun fetchNumberFact(request: NumberFactRequest): Response<NumberFactResponse> =
        retrofit.create(NumbersApiService::class.java)
            .getNumberFactAsync(number = request.number.toString())

}