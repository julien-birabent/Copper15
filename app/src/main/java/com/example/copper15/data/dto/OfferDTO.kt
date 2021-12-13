package com.example.copper15.data.dto

import com.google.gson.annotations.SerializedName

data class OfferDTO(
    @SerializedName("offer_id")
    val id : String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("cash_back")
    val cashBack: Float
)