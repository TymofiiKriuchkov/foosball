package com.tmg.foosball.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tmg.foosball.di.ViewModelFactory
import com.tmg.foosball.di.ViewModelKey
import com.tmg.foosball.ui.addResultGame.AddResultGameViewModel
import com.tmg.foosball.ui.allGames.AllGamesViewModel
import com.tmg.foosball.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllGamesViewModel::class)
    internal abstract fun bindAllGamesViewModel(viewModel: AllGamesViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddResultGameViewModel::class)
    internal abstract fun bindAddResultGameViewModel(viewModel: AddResultGameViewModel) : ViewModel
}