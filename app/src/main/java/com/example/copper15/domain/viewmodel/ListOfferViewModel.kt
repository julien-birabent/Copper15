package com.example.copper15.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.copper15.data.repository.ResultState
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.usecase.GetAllOffersUseCase
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListOfferViewModel @Inject constructor(getAllOffersUseCase: GetAllOffersUseCase) :
    ViewModel() {

    val allOffers : LiveData<ResultState<List<Offer>>> = getAllOffersUseCase.execute().toLiveData()

    fun <T> Observable<T>.toLiveData(): LiveData<T> {
        return LiveDataReactiveStreams.fromPublisher(this.toFlowable(BackpressureStrategy.DROP))
    }
}