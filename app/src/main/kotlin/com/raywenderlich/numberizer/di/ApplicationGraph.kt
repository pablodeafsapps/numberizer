package com.raywenderlich.numberizer.di

import android.content.Context
import com.raywenderlich.numberizer.datalayer.di.DatasourceModule
import com.raywenderlich.numberizer.datalayer.di.RepositoryModule
import com.raywenderlich.numberizer.domainlayer.di.UsecaseModule
import com.raywenderlich.numberizer.presentationlayer.di.ApplicationScope
import com.raywenderlich.numberizer.presentationlayer.di.MainComponent
import com.raywenderlich.numberizer.presentationlayer.di.PresentationlayerModule
import com.raywenderlich.numberizer.presentationlayer.di.SplashComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@ApplicationScope
@Component(
    modules = [UtilsModule::class, PresentationlayerModule::class, UsecaseModule::class,
        RepositoryModule::class, DatasourceModule::class]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(modules: UtilsModule): ApplicationComponent
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