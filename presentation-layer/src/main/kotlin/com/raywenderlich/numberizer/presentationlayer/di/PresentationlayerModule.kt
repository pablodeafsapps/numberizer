package com.raywenderlich.numberizer.presentationlayer.di

import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract.Presenter.Companion.MAIN_PRESENTER_TAG
import com.raywenderlich.numberizer.presentationlayer.feature.main.MainContract.View.Companion.MAIN_VIEW_TAG
import com.raywenderlich.numberizer.presentationlayer.feature.main.presenter.MainPresenter
import com.raywenderlich.numberizer.presentationlayer.feature.main.view.ui.MainActivity
import com.raywenderlich.numberizer.presentationlayer.feature.splash.SplashContract
import com.raywenderlich.numberizer.presentationlayer.feature.splash.SplashContract.Presenter.Companion.SPLASH_PRESENTER_TAG
import com.raywenderlich.numberizer.presentationlayer.feature.splash.SplashContract.View.Companion.SPLASH_VIEW_TAG
import com.raywenderlich.numberizer.presentationlayer.feature.splash.presenter.SplashPresenter
import com.raywenderlich.numberizer.presentationlayer.feature.splash.view.ui.SplashActivity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@Module(subcomponents = [SplashComponent::class, MainComponent::class])
object PresentationlayerModule

interface SplashComponentFactoryProvider {
    fun provideSplashComponentFactory(): SplashComponent.Factory
}

@ActivityScope
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(module: SplashModule): SplashComponent
    }

    fun inject(activity: SplashActivity)

}

@Module
class SplashModule(private val activity: SplashActivity) {

    @ActivityScope
    @Provides
    @Named(SPLASH_VIEW_TAG)
    fun provideSplashView(): SplashContract.View = activity

    @ActivityScope
    @Provides
    @Named(SPLASH_PRESENTER_TAG)
    fun provideSplashPresenter(presenter: SplashPresenter): SplashContract.Presenter = presenter

}

interface MainComponentFactoryProvider {
    fun provideMainComponentFactory(): MainComponent.Factory
}

@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(module: MainModule): MainComponent
    }

    fun inject(activity: MainActivity)

}

@Module
class MainModule(private val activity: MainActivity) {

    @ActivityScope
    @Provides
    @Named(MAIN_VIEW_TAG)
    fun provideMainView(): MainContract.View = activity

    @ActivityScope
    @Provides
    @Named(MAIN_PRESENTER_TAG)
    fun provideMainPresenter(presenter: MainPresenter): MainContract.Presenter = presenter

}