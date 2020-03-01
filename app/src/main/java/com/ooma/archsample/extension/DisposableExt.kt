package com.ooma.archsample.extension

import com.ooma.archsample.presentation.viewmodel.DisposableViewModel
import io.reactivex.disposables.Disposable

fun Disposable.disposeBy(viewModel: DisposableViewModel): Disposable {
    viewModel.addDisposable(this)
    return this
}