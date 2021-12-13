package com.example.copper15.data.service

import com.example.copper15.data.dto.OffersBatchDTO
import io.reactivex.Single
import javax.inject.Inject

class RemoteOfferService @Inject constructor(private val offerApi: OfferApi): OfferService {

    override fun getAllOffers(): Single<OffersBatchDTO> = offerApi.getAllOffers()
}