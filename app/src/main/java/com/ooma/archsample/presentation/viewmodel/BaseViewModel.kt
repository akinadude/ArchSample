package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), DisposableViewModel {

    //todo it can be some Failure type if we want
    protected val _failure: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }
    protected val _loading: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    val failure: MutableLiveData<Throwable>
        get() = _failure
    val loading: MutableLiveData<Unit>
        get() = _loading

    protected fun setFailure(failure: Throwable) {
        _failure.value = failure
    }

    protected fun setLoading() {
        _loading.value = Unit
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}