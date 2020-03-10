package com.ooma.archsample.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.ooma.archsample.Navigator
import com.ooma.archsample.presentation.viewmodel.StartViewModelFactory

inline fun <reified T : ViewModel> Fragment.viewModel(body: T.() -> Unit): T {
    //todo Need to get rid of deprecated method
    val viewModel = ViewModelProviders.of(this)[T::class.java]
    viewModel.body()
    return viewModel
}

inline fun <reified T : ViewModel> Fragment.viewModel2(navigator: Navigator, body: T.() -> Unit): T {
    val factory = StartViewModelFactory(navigator)
    val viewModel = ViewModelProviders.of(this, factory)[T::class.java]
    viewModel.body()
    return viewModel
}