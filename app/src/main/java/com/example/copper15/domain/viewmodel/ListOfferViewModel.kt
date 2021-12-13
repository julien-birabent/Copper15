package com.example.copper15.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.copper15.data.repository.ResultState
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.model.SortOfferCriteria
import com.example.copper15.domain.usecase.GetAllOffersUseCase
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.combineLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListOfferViewModel @Inject constructor(getAllOffersUseCase: GetAllOffersUseCase) :
    ViewModel() {

    //region Sorting logic and variables
    private val _sortByNameEnabled: BehaviorProcessor<Pair<SortOfferCriteria, Boolean>> =
        BehaviorProcessor.createDefault(SortOfferCriteria.ByName() to false)

    private val _sortByCashBackEnabled: BehaviorProcessor<Pair<SortOfferCriteria, Boolean>> =
        BehaviorProcessor.createDefault(SortOfferCriteria.ByCashBack() to false)

    private val sortingCriterias: Flowable<List<SortOfferCriteria>> =
        listOf(_sortByNameEnabled, _sortByCashBackEnabled).combineLatest { sortCriteriaList ->
            sortCriteriaList.filter { (_, isCriteriaEnabled) ->
                isCriteriaEnabled
            }.map { (sorter, _) ->
                sorter
            }
        }

    fun sortOfferByName(isEnabled: Boolean) =
        _sortByNameEnabled.onNext(SortOfferCriteria.ByName() to isEnabled)

    fun sortOfferByCashBack(isEnabled: Boolean) =
        _sortByCashBackEnabled.onNext(SortOfferCriteria.ByCashBack() to isEnabled)
    //endregion

    //region List of offers logic and variable
    private val retryFetchOffers: BehaviorProcessor<Unit> = BehaviorProcessor.create()

    fun retryToFetchOffers() = retryFetchOffers.onNext(Unit)

    private val _allOffers: Flowable<ResultState<List<Offer>>> = sortingCriterias
        .flatMap { getAllOffersUseCase.execute(it) }
        .distinctUntilChanged()
        .retryWhen(retryFetchOffers)

    val allOffers: LiveData<ResultState<List<Offer>>> = _allOffers.toLiveData()

    val isLoadingOffers: LiveData<Boolean> = _allOffers
        .map {
            it is ResultState.Loading
        }
        .distinctUntilChanged()
        .toLiveData()
    //endregion

    // This will re-emit when the retry flowable emits.
    private fun <T> Flowable<T>.retryWhen(retry: Flowable<Unit>): Flowable<T> =
        Flowable.merge(Flowable.just(Unit), retry)
            .switchMap {
                this
            }
}