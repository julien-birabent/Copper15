package com.example.copper15.data.json

import android.content.Context
import androidx.annotation.RawRes
import com.example.copper15.R
import com.example.copper15.data.dto.OfferDTO
import com.example.copper15.data.dto.OffersBatchDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferJsonDataSource @Inject constructor(
    private val applicationContext: Context,
    private val gson: Gson
) {

    private val cacheData = listOf<OfferDTO>()

    fun loadAllOffers(): List<OfferDTO> =
        if (cacheData.isEmpty()) readRawJson<OffersBatchDTO>(R.raw.c51).offers else cacheData

    private inline fun <reified T> readRawJson(@RawRes rawResId: Int): T {
        applicationContext.resources.openRawResource(rawResId).bufferedReader().use {
            return gson.fromJson<T>(it, object : TypeToken<T>() {}.type)
        }
    }
}