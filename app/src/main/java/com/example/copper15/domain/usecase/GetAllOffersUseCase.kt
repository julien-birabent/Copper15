package com.example.copper15.domain.usecase

import com.example.copper15.data.repository.OfferRepository
import com.example.copper15.data.repository.ResultState
import com.example.copper15.data.repository.data
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.model.SortOfferCriteria
import com.example.copper15.thread.AppSchedulerProvider
import com.example.copper15.thread.SchedulerProvider
import io.reactivex.Flowable
import javax.inject.Inject


class GetAllOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository,
    schedulerProvider: SchedulerProvider
) : AsyncUseCase<List<SortOfferCriteria>, ResultState<List<Offer>>>(schedulerProvider) {

    override fun build(params: List<SortOfferCriteria>): Flowable<ResultState<List<Offer>>> {
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
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
    }

}