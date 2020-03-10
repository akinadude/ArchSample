package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ooma.archsample.Navigator

class StartViewModelFactory(private val navigator: Navigator) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartViewModel(navigator) as T
    }
}