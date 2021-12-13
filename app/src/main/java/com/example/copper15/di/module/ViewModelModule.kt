package com.example.copper15.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.copper15.di.ViewModelFactory
import com.example.copper15.di.ViewModelKey
import com.example.copper15.domain.viewmodel.ListOfferViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListOfferViewModel::class)
    abstract fun bindListOfferViewModel(viewModel: ListOfferViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}