package com.example.copper15.data.mapper

import com.example.copper15.data.database.OfferEntity
import com.example.copper15.domain.model.Offer
import javax.inject.Inject

class OfferModelAssembler @Inject constructor() {

    fun assemble(offerEntity: OfferEntity): Offer = offerEntity.run {
        Offer(id.toString(), name, imageUrl, cashBack)
    }

    fun assembleFromList(offerEntities: List<OfferEntity>): List<Offer> =
        offerEntities.map { assemble(it) }
}