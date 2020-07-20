package com.raywenderlich.numberizer.domainlayer.di

import com.raywenderlich.numberizer.domainlayer.DomainlayerContract
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactRequest
import com.raywenderlich.numberizer.domainlayer.domain.NumberFactResponse
import com.raywenderlich.numberizer.domainlayer.feature.main.MAIN_DOMAIN_LAYER_BRIDGE_TAG
import com.raywenderlich.numberizer.domainlayer.feature.main.MainDomainLayerBridge
import com.raywenderlich.numberizer.domainlayer.feature.main.MainDomainLayerBridgeImpl
import com.raywenderlich.numberizer.domainlayer.usecase.FETCH_NUMBER_FACT_UC_TAG
import com.raywenderlich.numberizer.domainlayer.usecase.FetchNumberFactUc
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object BridgeModule {

    @Provides
    @Named(MAIN_DOMAIN_LAYER_BRIDGE_TAG)
    fun provideMainBridge(bridge: MainDomainLayerBridgeImpl): MainDomainLayerBridge = bridge

}

@Module
object UsecaseModule {

    @Provides
    @Named(FETCH_NUMBER_FACT_UC_TAG)
    fun provideFetchNumberFactUc(usecase: FetchNumberFactUc): @JvmSuppressWildcards DomainlayerContract.Presentation.UseCase<NumberFactRequest, NumberFactResponse> =
        usecase

}