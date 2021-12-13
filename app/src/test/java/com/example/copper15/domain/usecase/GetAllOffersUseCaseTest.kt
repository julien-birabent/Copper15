package com.example.copper15.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.copper15.data.repository.OfferRepository
import com.example.copper15.data.repository.ResultState
import com.example.copper15.data.repository.data
import com.example.copper15.domain.TestSchedulerProvider
import com.example.copper15.domain.model.SortOfferCriteria
import com.example.copper15.domain.viewmodel.OfferMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test

class GetAllOffersUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testScheduler = TestSchedulerProvider
    private val offerRepositoryMock: OfferRepository = mockk()

    private val useCase: GetAllOffersUseCase by lazy {
        GetAllOffersUseCase(
            offerRepositoryMock,
            testScheduler
        )
    }

    @Test
    fun `GIVEN (a list of offer not ordered) WHEN (the use case is executed with no sorting parameter) THEN(the offers are successfully emited in the original order)`() {
        //GIVEN
        val offersNotSorted = OfferMocks.offersInAlphabeticalOrder
        val sortingCriterias = emptyList<SortOfferCriteria>()
        every { offerRepositoryMock.getAllOffers() } returns  Flowable.just(ResultState.Success(offersNotSorted))

        //WHEN
        val testSubscriber = useCase.execute(sortingCriterias).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { resultState ->
            assert(resultState is ResultState.Success<*>)

            !resultState.data.isNullOrEmpty() && resultState.data!!.zip(offersNotSorted).all { it.first.id == it.second.id }
        }
    }

    @Test
    fun `GIVEN (a list of offer not ordered) WHEN (the use case is executed with a sorting parameter by name) THEN(the offers are successfully emited and ordered by alphabetical order)`() {
        //GIVEN
        val offersNotSorted = OfferMocks.offersInAlphabeticalOrder.shuffled()
        val sortingCriterias = listOf(SortOfferCriteria.ByName())
        every { offerRepositoryMock.getAllOffers() } returns  Flowable.just(ResultState.Success(offersNotSorted))

        //WHEN
        val testSubscriber = useCase.execute(sortingCriterias).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { resultState ->
            assert(resultState is ResultState.Success<*>)

            !resultState.data.isNullOrEmpty() && resultState.data!!.zipWithNext { offer1, offer2 -> offer1.name <= offer2.name }.all { it }
        }
    }

    @Test
    fun `GIVEN (a list of offer not ordered) WHEN (the use case is executed with a sorting parameter by cash back) THEN(the offers are successfully emited and ordered by cash back ascending value order)`() {
        //GIVEN
        val offersNotSorted = OfferMocks.offersInAlphabeticalOrder.shuffled()
        val sortingCriterias = listOf(SortOfferCriteria.ByCashBack())
        every { offerRepositoryMock.getAllOffers() } returns  Flowable.just(ResultState.Success(offersNotSorted))

        //WHEN
        val testSubscriber = useCase.execute(sortingCriterias).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { resultState ->
            assert(resultState is ResultState.Success<*>)

            !resultState.data.isNullOrEmpty() && resultState.data!!.zipWithNext { offer1, offer2 -> offer1.cashBack <= offer2.cashBack }.all { it }
        }
    }
}