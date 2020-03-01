package com.ooma.archsample.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.viewModel(body: T.() -> Unit): T {
    val viewModel = ViewModelProviders.of(this)[T::class.java]
    viewModel.body()
    return viewModel
}