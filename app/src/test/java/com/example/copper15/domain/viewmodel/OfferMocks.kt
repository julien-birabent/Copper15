package com.example.copper15.domain.viewmodel

import com.example.copper15.domain.model.Offer

object OfferMocks {

    val offersInAlphabeticalOrder = listOf(
        Offer("1","A", "", 0f),
        Offer("2","B", "", 1f),
        Offer("3","C", "", 2f),
        Offer("4","D", "", 3f)
    )

    val offersInAscendingCashbackOrder = offersInAlphabeticalOrder.toList()
}