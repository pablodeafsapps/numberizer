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
package com.raywenderlich.numberizer.base

import android.app.Application
import com.raywenderlich.numberizer.datalayer.di.DaggerDatalayerComponent
import com.raywenderlich.numberizer.di.ApplicationComponent
import com.raywenderlich.numberizer.di.DaggerApplicationComponent
import com.raywenderlich.numberizer.di.UtilsModule
import com.raywenderlich.numberizer.presentationlayer.di.*

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'LeakCanary' for memory leaks, and 'Dagger' for dependency
 * inversion are initialized here.
 */
class BaseApplication : Application(), SplashComponentFactoryProvider, MainComponentFactoryProvider {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        /*
         'ApplicationComponent' is created including all data every associated component needs.
         Specifically, 'modules' parameters refer to those which demand external variables (mostly
         'Context' instances), whereas 'dependencies' relate to other components which need external
         objects to build up their own modules.
         */
        appComponent = DaggerApplicationComponent.factory().create(
            modules = UtilsModule(ctx = this),
//            domainlayerComponent = DaggerDomainlayerComponent.factory().create()
//            datalayerComponent = DaggerDatalayerComponent.factory().create()
        )
    }
    
    override fun provideSplashComponentFactory(): SplashComponent.Factory =
        appComponent.splashComponentFactory()

    override fun provideMainComponentFactory(): MainComponent.Factory =
        appComponent.mainComponentFactory()

}