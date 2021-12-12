package com.example.copper15.data.repository

import android.util.Log
import com.example.copper15.data.database.OfferDao
import com.example.copper15.data.dto.OfferDTO
import com.example.copper15.data.service.LocalJsonOfferService
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
        val loadingEmission = Flowable.just(ResultState.Loading(listOf<Offer>()))

        val actualDataEmission = offerService.getAllOffers()
            .toFlowable()
            .map { persistOffers(it.offers) }
            .flatMap { offerDao.loadAllOffers() }
            .map { offerModelAssembler.assembleFromList(it) }
            .map { ResultState.Success(it) }

        return Flowable.concat(loadingEmission, actualDataEmission)
            .onErrorReturn {
                Log.e("TEST", it.toString())
                ResultState.Error(it, null)
            }
    }

    private fun persistOffers(offerDTO: List<OfferDTO>) {
        val offerEntities = offerDTO.map { offerEntityMapper.map(it) }
        offerDao.insertOffers(*offerEntities.toTypedArray())
    }
}