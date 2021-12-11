package com.example.copper15.domain.usecase

import com.example.copper15.thread.AppSchedulerProvider
import io.reactivex.Flowable


abstract class AsyncUseCase<in Params, Results>(protected val appSchedulerProvider: AppSchedulerProvider) {

    abstract fun build(params: Params? = null): Flowable<Results>

    fun execute(params: Params? = null): Flowable<Results> = build(params)

}