package com.ooma.archsample.support.rx

import io.reactivex.Single

object RxStubsCreator {

    fun <T> wrapSingle(items: List<T>): Single<List<T>> = Single.just(items)

    fun <T> wrapSingle(item: T): Single<T> = Single.just(item)

    fun <T> wrapError(error: Exception): Single<T> = Single.error(error)

    fun <T> wrapError(errorMessage: String): Single<T> = Single.error(Exception(errorMessage))
}