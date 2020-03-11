package com.ooma.archsample.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ooma.archsample.Navigator
import com.ooma.archsample.presentation.viewmodel.SearchViewModel

class SearchViewModelFactory(private val navigator: Navigator) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(navigator) as T
    }
}