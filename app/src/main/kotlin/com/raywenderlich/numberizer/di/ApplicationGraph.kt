package com.raywenderlich.numberizer.di

import android.content.Context
import com.raywenderlich.numberizer.datalayer.di.DatalayerComponent
import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.di.DomainlayerComponent
import com.raywenderlich.numberizer.domainlayer.di.DomainlayerModule
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.domainlayer.usecase.FETCH_NUMBER_FACT_UC_TAG
import com.raywenderlich.numberizer.presentationlayer.di.*
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named

@ApplicationScope
@Component(
    modules = [UtilsModule::class, PresentationlayerModule::class, DomainlayerModule::class],
    dependencies = [DatalayerComponent::class]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(modules: UtilsModule, datalayerComponent: DatalayerComponent): ApplicationComponent
    }

    // downstream dependent components need data types to be exposed
    // 'subcomponents' do not need this exposure! :) (i.e. 'Context' is automatically reachable!)
    fun splashComponentFactory(): SplashComponent.Factory
    fun mainComponentFactory(): MainComponent.Factory

}

@Module
class UtilsModule(private val ctx: Context) {

    @ApplicationScope
    @Provides
    fun provideApplicationContext(): Context = ctx

}