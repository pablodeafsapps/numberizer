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
import arrow.core.right
import com.nhaarman.mockitokotlin2.*
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val DEFAULT_INTEGER_VALUE = -1
private const val DEFAULT_STRING_VALUE = "none"

@ExperimentalCoroutinesApi
class FetchNumberFactUcTest {

    private lateinit var usecase: DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse>
    private lateinit var mockRepository: DomainlayerContract.Data.DataRepository<NumberFactResponse>

    @Before
    fun setUp() {
        mockRepository = mock()
        usecase = FetchNumberFactUc(numberDataRepository = mockRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Given null parameters, when usecase is invoked -- 'InputParamsError' is returned`() = runBlockingTest {
        // given
        val nullParams = null
        // when
        val response = usecase.run(params = nullParams)
        // then
        Assert.assertTrue(response.isLeft() && (response as? Either.Left<Failure>)?.a is Failure.InputParamsError)
    }

    // TODO: this test fails because the 'repository' instance is not accessible, i.e. cannot be mocked/stubbed
    @Test
    fun `Given right parameters, when usecase is invoked -- 'NumberFactResponse' data is returned`() = runBlockingTest {
        // given
        val rightParams = NumberFactRequest(number = DEFAULT_INTEGER_VALUE)
        whenever(mockRepository.fetchNumberFact(request = rightParams)).doReturn(getDummyNumberFactResponse().right())
        // when
        val response = usecase.run(params = rightParams)
        // then
        Assert.assertTrue(response.isRight() && (response as? Either.Right<NumberFactResponse>) != null)
    }

    private fun getDummyNumberFactResponse() = NumberFactResponse(fact = DEFAULT_STRING_VALUE)

}
