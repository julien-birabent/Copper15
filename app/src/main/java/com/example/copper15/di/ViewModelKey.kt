package com.example.copper15.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(value = RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)