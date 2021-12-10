package com.example.copper15.data.mapper

import com.example.copper15.data.database.OfferEntity
import com.example.copper15.data.dto.OfferDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferEntityMapper @Inject constructor(){

    fun map(offerDTO: OfferDTO): OfferEntity =
        OfferEntity(
            id = offerDTO.id.toInt(),
            name = offerDTO.name,
            imageUrl = offerDTO.imageUrl,
            cashBack = offerDTO.cashBack
        )
}