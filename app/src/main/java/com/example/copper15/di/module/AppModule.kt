package com.example.copper15.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.copper15.thread.AppSchedulerProvider
import com.example.copper15.thread.SchedulerProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .setLenient()
            .create()
    }
}

