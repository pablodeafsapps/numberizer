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
package com.raywenderlich.numberizer.presentationlayer.feature.splash.view.ui

import android.app.Activity
import android.content.Intent
import com.raywenderlich.numberizer.presentationlayer.feature.main.view.ui.MainActivity
import com.raywenderlich.numberizer.presentationlayer.feature.splash.SplashContract
import com.raywenderlich.numberizer.presentationlayer.feature.splash.presenter.SplashPresenter

const val SPLASH_VIEW_TAG = "splashView"

class SplashActivity : Activity(), SplashContract.View {

    private val presenter: SplashContract.Presenter by lazy { SplashPresenter(view = this) }

    override fun onResume() {
        super.onResume()
        presenter.onViewResumed()
    }

    override fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun finishView() {
        finish()
    }

}