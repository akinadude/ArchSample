package com.ooma.archsample.domain.usecase

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class UseCase<out Type, in Params> where Type : Any {

    //todo It is hardly coupled with Single and thread switching. Need to fix it!
    abstract fun run(params: Params): Single<out Type>

    fun execute(
        params: Params,
        onSuccess: (Type) -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ): Disposable =
        run(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onFailure)

    class None
}