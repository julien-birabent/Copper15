package com.example.copper15.data.service

import com.example.copper15.data.dto.OffersBatchDTO
import io.reactivex.Single

interface OfferService {
    fun getAllOffers() : Single<OffersBatchDTO>
}