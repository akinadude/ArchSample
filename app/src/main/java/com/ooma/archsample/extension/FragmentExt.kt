package com.ooma.archsample.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.viewModel(body: T.() -> Unit): T {
    val viewModel = ViewModelProvider(this)[T::class.java]
    viewModel.body()
    return viewModel
}

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val viewModel = ViewModelProvider(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}