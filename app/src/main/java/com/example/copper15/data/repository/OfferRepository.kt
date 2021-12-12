package com.example.copper15.data.repository

import com.example.copper15.data.database.OfferDao
import com.example.copper15.data.dto.OfferDTO
import com.example.copper15.data.mapper.OfferEntityMapper
import com.example.copper15.data.mapper.OfferModelAssembler
import com.example.copper15.data.service.OfferService
import com.example.copper15.domain.model.Offer
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(
    private val offerService: OfferService,
    private val offerDao: OfferDao,
    private val offerEntityMapper: OfferEntityMapper,
    private val offerModelAssembler: OfferModelAssembler
) {
    fun getAllOffers(): Flowable<ResultState<List<Offer>>> {
        return Flowable.concatDelayError(listOf(loadCachedData(), fetchDataFromService()))
            .onErrorReturn {
                ResultState.Error(it, null)
            }
    }

    private fun fetchDataFromService() : Flowable<ResultState<List<Offer>>> =
        offerService.getAllOffers()
        .map { persistOffers(it.offers) }
        .flatMap { offerDao.loadAllOffers() }.toFlowable()
        .map { offerModelAssembler.assembleFromList(it) }
        .map { ResultState.Success(it) }


    private fun loadCachedData() : Flowable<ResultState<List<Offer>>> {
        val cachedData = offerDao.loadAllOffers().toFlowable()
        return cachedData.map { ResultState.Loading(offerModelAssembler.assembleFromList(it)) }
    }

    private fun persistOffers(offerDTO: List<OfferDTO>) {
        val offerEntities = offerDTO.map { offerEntityMapper.map(it) }
        offerDao.insertOffers(*offerEntities.toTypedArray())
    }
}