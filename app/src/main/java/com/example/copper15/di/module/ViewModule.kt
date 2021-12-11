package com.example.copper15.di.module

import com.example.copper15.ui.screen.offers.OfferListFragment
import com.example.copper15.ui.screen.offers.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeListOfferFragment(): OfferListFragment
}