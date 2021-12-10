package com.example.copper15.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "copper15-database"

@Database(entities = [OfferEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun offerDao(): OfferDao
}