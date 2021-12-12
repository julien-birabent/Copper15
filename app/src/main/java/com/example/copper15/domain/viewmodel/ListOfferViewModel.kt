package com.example.copper15.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.copper15.data.repository.ResultState
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.usecase.GetAllOffersUseCase
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListOfferViewModel @Inject constructor(getAllOffersUseCase: GetAllOffersUseCase) :
    ViewModel() {


    private val _allOffers : Flowable<ResultState<List<Offer>>> = getAllOffersUseCase.execute()

    val allOffers : LiveData<ResultState<List<Offer>>> = getAllOffersUseCase.execute()
        .toLiveData()

    val isLoadingOffers : LiveData<Boolean> = _allOffers
        .takeUntil { it !is ResultState.Loading }
        .map {
            it is ResultState.Loading
        }
        .distinctUntilChanged()
        .toLiveData()

}