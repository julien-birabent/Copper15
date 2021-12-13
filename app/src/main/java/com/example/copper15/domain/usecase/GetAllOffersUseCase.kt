package com.example.copper15.domain.usecase

import com.example.copper15.data.repository.OfferRepository
import com.example.copper15.data.repository.ResultState
import com.example.copper15.data.repository.data
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.model.OfferSort
import com.example.copper15.thread.AppSchedulerProvider
import io.reactivex.Flowable
import javax.inject.Inject


class GetAllOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository,
    appSchedulerProvider: AppSchedulerProvider
) : AsyncUseCase<List<OfferSort>, ResultState<List<Offer>>>(appSchedulerProvider) {

    override fun build(params: List<OfferSort>): Flowable<ResultState<List<Offer>>> {
        return offerRepository.getAllOffers()
            .map { resultState ->
                val resultStateData = if (params.isEmpty()) {
                    resultState.data ?: emptyList()
                } else {
                    val sortingRules = params.map { it.sortingRule }
                    resultState.data?.sortedWith(compareBy(*sortingRules.toTypedArray()))
                        ?: emptyList()
                }
                ResultState.createFromSameType(resultState, resultStateData)
            }
            .cache()
            .subscribeOn(appSchedulerProvider.io())
            .observeOn(appSchedulerProvider.mainThread())
    }

}