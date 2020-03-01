package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), DisposableViewModel {

    //todo it can be some Failure type if we want
    var failure: MutableLiveData<Throwable> = MutableLiveData()

    protected fun handleFailure(failure: Throwable) {
        this.failure.value = failure
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