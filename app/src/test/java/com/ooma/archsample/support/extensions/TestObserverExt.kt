package com.ooma.archsample.support.extensions

import io.reactivex.observers.TestObserver

fun <T> TestObserver<T>.assertNoErrorsHasValues(vararg values: T) =
    this.apply {
        assertNoErrors()
        assertValues(*values)
    }

fun <T> TestObserver<T>.assertNoErrorsNoValues() =
    this.apply {
        assertNoErrors()
        assertNoValues()
    }