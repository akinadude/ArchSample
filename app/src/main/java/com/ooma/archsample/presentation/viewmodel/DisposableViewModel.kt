package com.ooma.archsample.presentation.viewmodel

import io.reactivex.disposables.Disposable

interface DisposableViewModel {
    fun addDisposable(disposable: Disposable)
}