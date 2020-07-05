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
package com.raywenderlich.numberizer.presentationlayer.feature.main.presenter

import arrow.core.Either
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactCategory
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val DEFAULT_STRING_VALUE = "none"

class MainPresenterTest {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var mockView: MainContract.View
    private lateinit var mockUsecase: DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse>

    @Before
    fun setUp() {
        mockView = mock()
        mockUsecase = mock()
        mainPresenter = MainPresenter(view = mockView)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Given blank data, when 'onFetchFactSelected' is invoked -- 'displayInputError' is triggered`() {
        // given
        val blankDdata = "   "
        // when
        mainPresenter.onFetchFactSelected(data = blankDdata, category = NumberFactCategory.TRIVIA)
        // then
        verify(mockView).displayInputError(error = any())
    }

    @Test
    fun `Given no numeric data, when 'onFetchFactSelected' is invoked -- 'displayInputError' is triggered`() {
        // given
        val noNumericData = "not a number"
        // when
        mainPresenter.onFetchFactSelected(data = noNumericData, category = NumberFactCategory.TRIVIA)
        // then
        verify(mockView).displayInputError(error = any())
    }

    // TODO: this test fails because the 'usecase' instance is not accessible, i.e. cannot be mocked/stubbed
    @Test
    fun `Given data, when 'onFetchFactSelected' is invoked -- 'displayNumberFact' is triggered`() {
        // given
        val requestData = "3"
        val argumentCaptor = argumentCaptor<(Either<Failure, NumberFactResponse>) -> Unit>()
        // when
        mainPresenter.onFetchFactSelected(data = requestData, category = NumberFactCategory.TRIVIA)
        verify(mockUsecase).invoke(scope = any(), params = any(), onResult = argumentCaptor.capture())
        argumentCaptor.firstValue.invoke(getDummyNumberFactResponse().right())
        // then
        verify(mockView).displayNumberFact(numberFact = any())
    }

    @Test
    fun `Given unformattable data, when 'onFetchFactSelected' is invoked -- 'displayInputError' is triggered`() {
        // given
        val requestData = "none"
        // when
        mainPresenter.onFetchFactSelected(data = requestData, category = NumberFactCategory.TRIVIA)
        // then
        verify(mockView).displayInputError(error = any())
    }

    private fun getDummyNumberFactResponse() = NumberFactResponse(fact = DEFAULT_STRING_VALUE)

}