package com.example.copper15.data.repository

import com.example.copper15.data.database.OfferDao
import com.example.copper15.data.dto.OfferDTO
import com.example.copper15.data.json.OfferJsonDataSource
import com.example.copper15.data.mapper.OfferEntityMapper
import com.example.copper15.data.mapper.OfferModelAssembler
import com.example.copper15.domain.model.Offer
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(
    private val offerJsonDataSource: OfferJsonDataSource,
    private val offerEntityMapper: OfferEntityMapper,
    private val offerModelAssembler: OfferModelAssembler,
    private val offerDao: OfferDao
) {
    fun getAllOffers(): Flowable<out ResultState<List<Offer>>> {
        val loadingEmission = Flowable.just(ResultState.Loading(listOf<Offer>()))

        val actualDataEmission = Flowable.just(offerJsonDataSource.loadAllOffers())
            .map { persistOffers(it) }
            .flatMap { offerDao.loadAllOffers() }
            .map { offerModelAssembler.assembleFromList(it) }
            .map { ResultState.Success(it) }

        return Flowable.concat(loadingEmission, actualDataEmission)
    }

    private fun persistOffers(offerDTO: List<OfferDTO>) {
        val offerEntities = offerDTO.map { offerEntityMapper.map(it) }
        offerDao.insertOffers(*offerEntities.toTypedArray())
    }
}