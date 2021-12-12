package com.example.copper15.domain.model

sealed class OfferSort {

    abstract val sortingRule: (Offer) -> Comparable<*>?

    class ByName(override val sortingRule: (Offer) -> Comparable<*>? = { it.name }) : OfferSort()
    class ByCashBack(override val sortingRule: (Offer) -> Comparable<*>? = {it.cashBack}) : OfferSort()
}
