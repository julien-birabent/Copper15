package com.example.copper15.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.copper15.data.repository.ResultState
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.model.OfferSort
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
    private val _sortByNameEnabled: BehaviorProcessor<Pair<OfferSort, Boolean>> =
        BehaviorProcessor.createDefault(OfferSort.ByName() to false)

    val sortByNameEnabled: LiveData<Boolean>
        get() = _sortByNameEnabled.map { (_, isEnabled) -> isEnabled }.toLiveData()

    private val _sortByCashBackEnabled: BehaviorProcessor<Pair<OfferSort, Boolean>> =
        BehaviorProcessor.createDefault(OfferSort.ByCashBack() to false)

    val sortByNameCashBack: LiveData<Boolean>
        get() = _sortByCashBackEnabled.map { (_, isEnabled) -> isEnabled }.toLiveData()

    private val sortOffers: Flowable<List<OfferSort>> =
        listOf(_sortByNameEnabled, _sortByCashBackEnabled).combineLatest { sorterList ->
            sorterList.filter { (_, isSorterEnabled) ->
                isSorterEnabled
            }.map { (sorter, _) ->
                sorter
            }
        }
    
    fun sortOfferByName(isEnabled: Boolean) =
        _sortByNameEnabled.onNext(OfferSort.ByName() to isEnabled)

    fun sortOfferByCashBack(isEnabled: Boolean) =
        _sortByCashBackEnabled.onNext(OfferSort.ByCashBack() to isEnabled)
    //endregion

    //region List of offers logic and variable
    private val _allOffers: Flowable<ResultState<List<Offer>>> = sortOffers
        .flatMap { getAllOffersUseCase.execute(it) }

    val allOffers: LiveData<ResultState<List<Offer>>> = _allOffers.toLiveData()

    val isLoadingOffers: LiveData<Boolean> = _allOffers
        .map {
            it is ResultState.Loading
        }
        .distinctUntilChanged()
        .toLiveData()
    //endregion
}