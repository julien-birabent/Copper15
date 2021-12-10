package com.example.copper15.di.module

import com.example.copper15.ui.ListOfferFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector
    abstract fun contributeListOfferFragment(): ListOfferFragment
}