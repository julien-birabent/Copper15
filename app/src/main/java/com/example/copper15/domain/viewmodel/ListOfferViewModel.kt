package com.example.copper15.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.copper15.data.repository.ResultState
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.usecase.GetAllOffersUseCase
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Flowables
import java.util.concurrent.TimeUnit
import java.util.function.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListOfferViewModel @Inject constructor(getAllOffersUseCase: GetAllOffersUseCase) :
    ViewModel() {


    private val _allOffers : Flowable<ResultState<List<Offer>>> = getAllOffersUseCase.execute()

    val allOffers : LiveData<ResultState<List<Offer>>> = getAllOffersUseCase.execute()
        .toLiveData()

    val isLoadingOffers : LiveData<Boolean> = _allOffers
        .map {
            Log.d("TEST", "isLoading + $it")
            it is ResultState.Loading
        }
        .toLiveData()

}