package com.ooma.archsample.domain.usecase

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<Type, Params> : BaseUseCase<Type, Params>() {

    abstract fun buildUseCaseSingle(params: Params): Single<Type>

    fun execute(
        params: Params,
        onSuccess: (Type) -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ): Disposable =
        buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onFailure)

    class None
}