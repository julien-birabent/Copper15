package com.example.copper15.domain.model

sealed class SortOfferCriteria {

    abstract val sortingRule: (Offer) -> Comparable<*>?

    class ByName(override val sortingRule: (Offer) -> Comparable<*>? = Offer::name) : SortOfferCriteria()
    class ByCashBack(override val sortingRule: (Offer) -> Comparable<*>? = Offer::cashBack) : SortOfferCriteria()
}
