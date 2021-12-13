package com.example.copper15.domain.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.example.copper15.data.repository.ResultState
import com.example.copper15.data.repository.data
import com.example.copper15.domain.model.Offer
import com.example.copper15.domain.usecase.GetAllOffersUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListOfferViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getAllOffersUseCaseMock: GetAllOffersUseCase = mockk()

    private val viewModel by lazy {
        ListOfferViewModel(getAllOffersUseCaseMock)
    }

    private val lifecycleRegistry = LifecycleRegistry(mockk())


    @Before
    fun setup() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @After
    fun doAfter() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    @Test
    fun `GIVEN (a use case that gives all the offers successfully) WHEN (the ui subscribes to the offers) THEN(the offers are successfully emited)`() {

        //GIVEN
        val offers = OfferMocks.offersInAlphabeticalOrder
        val useCaseProcessor = PublishProcessor.create<ResultState<List<Offer>>>()
        every { getAllOffersUseCaseMock.execute(any()) } returns useCaseProcessor

        //WHEN
        val testSubscriber = viewModel.allOffers.test(lifecycleRegistry)
        useCaseProcessor.onNext(ResultState.Success(offers))

        //THEN
        testSubscriber.assertValue {
            assert(it is ResultState.Success)
            !it.data.isNullOrEmpty() && it.data!!.containsAll(offers)
        }
    }

    @Test
    fun `GIVEN (the list of offer already displayed) WHEN (the user enables one of the sorting buttons) THEN(the list of offers is re-emited)`() {

        //GIVEN
        val offers = OfferMocks.offersInAlphabeticalOrder
        val useCaseProcessor = PublishProcessor.create<ResultState<List<Offer>>>()
        every { getAllOffersUseCaseMock.execute(any()) } returns useCaseProcessor

        //WHEN

        val testSubscriber = viewModel.allOffers.test(lifecycleRegistry)
        useCaseProcessor.onNext(ResultState.Success(offers))

        testSubscriber.assertValueCount(1)
        testSubscriber.assertOf { it is ResultState.Success<*> }
        viewModel.sortOfferByName(true)

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertOf { it is ResultState.Success<*> }
    }

    @Test
    fun `GIVEN (a use case is fetching the offers) WHEN (the ui subscribes to the offers) THEN(the loading state is emited before success or error)`() {

        //GIVEN
        val offers = OfferMocks.offersInAlphabeticalOrder
        val useCaseProcessor = PublishProcessor.create<ResultState<List<Offer>>>()
        every { getAllOffersUseCaseMock.execute(any()) } returns useCaseProcessor

        //WHEN
        val testSubscriber = viewModel.isLoadingOffers.test(lifecycleRegistry)
        useCaseProcessor.onNext(ResultState.Loading(offers))
        useCaseProcessor.onNext(ResultState.Success(offers))

        //THEN
        testSubscriber.assertValueCount(2)
        testSubscriber.assertValues(true, false)
    }

    @Test
    fun `GIVEN (a use case that fetches offers successfully) WHEN (retryToFetchOffers() is called and the batch of offers differ from the last one) THEN(the offers emit again)`() {

        //GIVEN
        val offers = OfferMocks.offersInAlphabeticalOrder
        val newBatchOfOffers = offers.shuffled()

        every { getAllOffersUseCaseMock.execute(any()) } returns Flowable.just(
            ResultState.Success(
                offers
            )
        )

        //WHEN
        val testSubscriber = viewModel.allOffers.test(lifecycleRegistry)
        every { getAllOffersUseCaseMock.execute(any()) } returns Flowable.just(
            ResultState.Success(
                newBatchOfOffers
            )
        )
        viewModel.retryToFetchOffers()

        //THEN
        testSubscriber.assertValueCount(2)
    }
}