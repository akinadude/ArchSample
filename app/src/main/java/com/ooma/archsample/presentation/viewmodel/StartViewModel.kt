package com.ooma.archsample.presentation.viewmodel

import com.ooma.archsample.Navigator

class StartViewModel : BaseViewModel() {

    private lateinit var navigator: Navigator

    fun onStartClick() {
        navigator.openUsersSearchScreen()
    }

    fun setNavigator(n: Navigator) {
        navigator = n
    }
}