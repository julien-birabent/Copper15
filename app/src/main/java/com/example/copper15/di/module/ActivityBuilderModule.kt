package com.example.copper15.di.module

import com.example.copper15.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity

}
