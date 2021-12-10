package com.example.copper15.thread

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSchedulerProvider @Inject constructor() : SchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
