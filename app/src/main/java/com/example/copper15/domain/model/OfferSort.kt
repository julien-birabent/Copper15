package com.example.copper15.domain.model

sealed class OfferSort {

    abstract val sortingRule: (Offer) -> Comparable<*>?

    class ByName(override val sortingRule: (Offer) -> Comparable<*>? = Offer::name) : OfferSort()
    class ByCashBack(override val sortingRule: (Offer) -> Comparable<*>? = Offer::cashBack) : OfferSort()
}
