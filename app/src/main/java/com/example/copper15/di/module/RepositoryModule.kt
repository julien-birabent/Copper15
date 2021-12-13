package com.example.copper15.di.module

import com.example.copper15.data.database.OfferDao
import com.example.copper15.data.mapper.OfferEntityMapper
import com.example.copper15.data.mapper.OfferModelAssembler
import com.example.copper15.data.repository.OfferRepository
import com.example.copper15.data.service.OfferService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideOfferRepository(
        offerService: OfferService,
        offerDao: OfferDao,
        offerEntityMapper: OfferEntityMapper,
        offerModelAssembler: OfferModelAssembler
    ): OfferRepository = OfferRepository(offerService,offerDao,offerEntityMapper,offerModelAssembler)
}