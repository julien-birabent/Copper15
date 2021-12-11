package com.example.copper15.ui.item

class OfferListItem<T>(
    var name: String,
    var imageResource : String,
    var cashback: String,
    data: T
): ListItemDataHolder<T>(data)