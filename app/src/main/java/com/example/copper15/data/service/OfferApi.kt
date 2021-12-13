package com.example.copper15.data.service

import com.example.copper15.data.dto.OffersBatchDTO
import io.reactivex.Single
import retrofit2.http.GET

interface OfferApi {

    @GET ("https://jsonkeeper.com/b/TU9Q")
    fun getAllOffers(): Single<OffersBatchDTO>
}