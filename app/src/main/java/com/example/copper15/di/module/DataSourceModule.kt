package com.example.copper15.di.module

import android.content.Context
import androidx.room.Room
import com.example.copper15.data.database.AppDatabase
import com.example.copper15.data.database.DATABASE_NAME
import dagger.Module
import dagger.Provides


@Module
class DataSourceModule {

    @Provides
    fun provideDatabase(applicationContext: Context) =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME).build()

}

