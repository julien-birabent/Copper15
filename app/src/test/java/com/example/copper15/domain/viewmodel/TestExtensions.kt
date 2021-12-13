package com.example.copper15.domain.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import io.reactivex.processors.PublishProcessor
import io.reactivex.subscribers.TestSubscriber

@VisibleForTesting
fun <T> LiveData<T>.test(lifecycle: LifecycleRegistry): TestSubscriber<T> {
    val processor = PublishProcessor.create<T>()
    val testSubscriber = processor.test()
    observe({ lifecycle }) {
        processor.onNext(it)
    }
    return testSubscriber
}