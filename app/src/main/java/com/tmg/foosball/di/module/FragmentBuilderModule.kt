package com.tmg.foosball.di.module

import com.tmg.foosball.ui.addResultGame.AddResultGameDialogFragment
import com.tmg.foosball.ui.allGames.AllGamesFragment
import com.tmg.foosball.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun initMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun initAllGamesFragment(): AllGamesFragment

    @ContributesAndroidInjector
    abstract fun initAddResultGameDialogFragment(): AddResultGameDialogFragment
}