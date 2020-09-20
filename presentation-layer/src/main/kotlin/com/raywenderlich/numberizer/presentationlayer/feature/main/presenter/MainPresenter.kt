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

import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactCategory
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.domainlayer.usecase.FETCH_NUMBER_FACT_UC_TAG
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract
import com.raywenderlich.numberizer.presentationlayer.feature.main.view.ui.MAIN_VIEW_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

const val MAIN_PRESENTER_TAG = "mainPresenter"

class MainPresenter @Inject constructor(
    @Named(MAIN_VIEW_TAG) private val view: MainContract.View,
    @Named(FETCH_NUMBER_FACT_UC_TAG) private val fetchNumberFactUc: @JvmSuppressWildcards DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse>
) : MainContract.Presenter {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onAttach(mvpView: MainContract.View) {
        // no need to implement it since view injection is handled by Dagger
    }

    override fun onDetach() {
        job.cancel()
    }

    override fun onFetchFactSelected(data: String, category: NumberFactCategory) {
        if (data.isNotBlank()) {
            view.showLoading()
            try {
                val request = NumberFactRequest(number = data.toInt(), category = category)
                fetchNumberFactUc.invoke(scope = this, params = request, onResult = {
                    view.hideLoading()
                    it.fold(::handleError, ::handleFetchNumberFactSuccess)
                })
            } catch (e: NumberFormatException) {
                view.hideLoading()
                handleInputError(failure = Failure.InputParamsError("Parameter must be a number"))
            }
        } else {
            handleInputError(failure = Failure.InputParamsError("A number is required"))
        }
    }

    private fun handleFetchNumberFactSuccess(response: NumberFactResponse) {
        view.displayNumberFact(numberFact = response)
    }

    private fun handleError(failure: Failure) {
        view.displayError(error = failure)
    }

    private fun handleInputError(failure: Failure) {
        view.displayInputError(error = failure)
    }

}