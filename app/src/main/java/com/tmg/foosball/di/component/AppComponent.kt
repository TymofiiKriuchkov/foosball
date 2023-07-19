package com.tmg.foosball.di.component

import com.tmg.foosball.App
import com.tmg.foosball.di.module.ActivityBuilderModule
import com.tmg.foosball.di.module.AppModule
import com.tmg.foosball.di.module.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ContextModule::class,
    AndroidInjectionModule::class,
    ActivityBuilderModule::class
])

interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App) : Builder

        fun build() : AppComponent
    }
}