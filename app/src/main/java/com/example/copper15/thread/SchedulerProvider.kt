package com.example.copper15.thread

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun mainThread(): Scheduler
}