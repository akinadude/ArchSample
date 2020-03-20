package com.ooma.archsample.presentation.viewmodel

import com.ooma.archsample.Navigator

class StartViewModel(private val navigator: Navigator) : BaseViewModel() {

    fun onStartClick() {
        navigator.openUsersSearchScreen()
    }
}