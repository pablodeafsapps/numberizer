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
package com.raywenderlich.numberizer.presentationlayer.feature.main.view.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.presentationlayer.databinding.ActivityMainBinding
import com.raywenderlich.numberizer.presentationlayer.di.MainComponent
import com.raywenderlich.numberizer.presentationlayer.di.MainComponentFactoryProvider
import com.raywenderlich.numberizer.presentationlayer.di.MainModule
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract.Presenter.Companion.MAIN_PRESENTER_TAG
import javax.inject.Inject
import javax.inject.Named

class MainActivity : Activity(), MainContract.View {

    @Inject
    @Named(MAIN_PRESENTER_TAG)
    lateinit var presenter: MainContract.Presenter
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getMainComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        initView()
        setContentView(viewBinding.root)
    }

    private fun initView() {
        with(viewBinding) {
            btnFetchFact.setOnClickListener {
                presenter.onFetchFactSelected(data = etNumber.text.toString())
            }
        }
    }

    override fun displayNumberFact(numberFact: NumberFactResponse) {
        viewBinding.tvNumberFact.text = numberFact.fact
    }

    override fun displayError(error: Failure) {
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
    }

    override fun displayInputError(error: Failure) {
        viewBinding.etNumber.error = error.msg
    }

    override fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        viewBinding.pbLoading.visibility = View.INVISIBLE
    }

}

private fun MainActivity.getMainComponent(): MainComponent =
    (application as MainComponentFactoryProvider).provideMainComponentFactory()
        .create(module = MainModule(this))