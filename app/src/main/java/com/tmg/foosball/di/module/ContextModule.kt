package com.tmg.foosball.di.module

import android.content.Context
import com.tmg.foosball.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    @Provides
    @Singleton
    internal fun provideContext(app: App) : Context {
        return app
    }
}