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
import android.widget.ArrayAdapter
import android.widget.Toast
import com.raywenderlich.numberizer.domainlayer.domain.Failure
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactCategory
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.presentationlayer.databinding.ActivityMainBinding
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract
import com.raywenderlich.numberizer.presentationlayer.feature.main.presenter.MainPresenter

private const val EMPTY_STRING = ""
const val MAIN_VIEW_TAG = "mainView"

class MainActivity : Activity(), MainContract.View {

    private lateinit var viewBinding: ActivityMainBinding
    private val presenter: MainContract.Presenter by lazy { MainPresenter(view = this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        initView()
        setContentView(viewBinding.root)
    }

    private fun initView() {
        with(viewBinding) {
            spFactType.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, NumberFactCategory.values())
            btnFetchFact.setOnClickListener {
                presenter.onFetchFactSelected(
                    data = etNumber.text.toString(),
                    category = NumberFactCategory.valueOf(spFactType.selectedItem.toString())
                )
            }
        }
    }

    override fun displayNumberFact(numberFact: NumberFactResponse) {
        with(viewBinding) {
            etNumber.setText(EMPTY_STRING)
            tvNumberFact.text = numberFact.fact
        }
    }

    override fun displayError(error: Failure) {
        viewBinding.etNumber.setText(EMPTY_STRING)
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
    }

    override fun displayInputError(error: Failure) {
        viewBinding.etNumber.error = error.msg
    }

    override fun showLoading() {
        with (viewBinding) {
            tvNumberFact.text = EMPTY_STRING
            pbLoading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        viewBinding.pbLoading.visibility = View.INVISIBLE
    }

}