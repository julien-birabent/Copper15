package com.example.copper15.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
data class OfferEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val cashBack: Float
)
