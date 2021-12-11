package com.example.copper15.domain.usecase

import com.example.copper15.data.repository.OfferRepository
import com.example.copper15.data.repository.ResultState
import com.example.copper15.domain.model.Offer
import com.example.copper15.thread.AppSchedulerProvider
import io.reactivex.Flowable
import javax.inject.Inject


class GetAllOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository,
    appSchedulerProvider: AppSchedulerProvider
) : AsyncUseCase<Unit, ResultState<List<Offer>>>(appSchedulerProvider) {

    override fun build(params: Unit?): Flowable<ResultState<List<Offer>>> {
        return offerRepository.getAllOffers()
            .subscribeOn(appSchedulerProvider.io())
            .observeOn(appSchedulerProvider.mainThread())
    }
}