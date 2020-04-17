package com.ooma.archsample.domain.usecase

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class SubjectUseCase<Type, Params> : BaseUseCase<Type, Params>() {

    abstract fun buildSubjectObservable(): Observable<out Type>

    private lateinit var disposable: Disposable

    protected val subject: PublishSubject<Params> = PublishSubject.create()

    protected fun setSubjectItem(param: Params) {
        subject.onNext(param)
    }

    fun observeSubject(
        onSuccess: (Type) -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ): Disposable {
        disposable = buildSubjectObservable().subscribe(onSuccess, onFailure)
        return disposable
    }

    fun dispose() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}