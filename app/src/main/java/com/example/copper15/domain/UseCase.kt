package com.example.copper15.domain

import com.example.copper15.app.ThisApplication
import com.example.copper15.thread.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


abstract class UseCase<Params, Results> {

    private val disposables = CompositeDisposable()

    private val injector: Injector = Injector()

    class Injector {
        @Inject
        lateinit var schedulers: SchedulerProvider

        init {
            ThisApplication.applicationComponent()?.inject(this)
        }
    }

    fun execute(parameters: Params): Observable<Results> {
        return this.buildUseCaseObservable(parameters).apply {
            subscribeOn(injector.schedulers.io())
                .observeOn(injector.schedulers.mainThread())
        }
    }

    protected abstract fun buildUseCaseObservable(params: Params): Observable<Results>

    protected open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

}