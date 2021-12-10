package com.example.copper15.app

import android.app.Application
import com.example.copper15.di.component.ApplicationComponent
import com.example.copper15.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ThisApplication : Application(), HasAndroidInjector {

    private lateinit var component: ApplicationComponent

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Any>

    init {
        instance = this
    }

    companion object {
        private var instance: ThisApplication? = null

        fun applicationComponent(): ApplicationComponent? = instance?.component
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingInjector

}