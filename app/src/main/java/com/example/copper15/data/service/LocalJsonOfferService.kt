package com.example.copper15.data.service

import android.content.Context
import androidx.annotation.RawRes
import com.example.copper15.R
import com.example.copper15.data.dto.OffersBatchDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalJsonOfferService @Inject constructor(
    private val applicationContext: Context,
    private val gson: Gson
) : OfferService{

    private var cacheData : OffersBatchDTO? = null

    private inline fun <reified T> readRawJson(@RawRes rawResId: Int): T {
        applicationContext.resources.openRawResource(rawResId).bufferedReader().use {
            return gson.fromJson(it, object : TypeToken<T>() {}.type)
        }
    }

    override fun getAllOffers(): Single<OffersBatchDTO> {
        val offers =  if (cacheData == null) readRawJson<OffersBatchDTO>(R.raw.c51) else cacheData
        return Single.just(offers)
    }
}