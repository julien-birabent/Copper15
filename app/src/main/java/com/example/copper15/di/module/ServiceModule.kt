package com.example.copper15.di.module

import com.example.copper15.data.service.OfferApi
import com.example.copper15.data.service.OfferService
import com.example.copper15.data.service.RemoteOfferService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            // The base url isn't needed but we still have to define it to avoid throwing an exception
            .baseUrl("http://localhost/")
            .build()

    @Provides
    @Singleton
    fun provideOfferApi(retrofit: Retrofit): OfferApi = retrofit.create(OfferApi::class.java)


    /*
    Here we could choose whether we want the local or the remote service for example based on a parameter
     */
    @Provides
    @Singleton
    fun provideOfferService(offerApi: OfferApi): OfferService = RemoteOfferService(offerApi)
}