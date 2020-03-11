package com.ooma.archsample.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ooma.archsample.Navigator
import com.ooma.archsample.presentation.viewmodel.StartViewModel

class StartViewModelFactory(private val navigator: Navigator) : ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return StartViewModel(navigator) as T
    }
}