package com.ooma.archsample.presentation.viewmodel

import com.ooma.archsample.Navigator

class StartViewModel(private val navigator: Navigator) : BaseViewModel() {

    //todo It should be constructor parameter, so the way of view model creation should be changed
    //private lateinit var navigator: Navigator

    fun onStartClick() {
        navigator.openUsersSearchScreen()
    }

    /*fun setNavigator(n: Navigator) {
        navigator = n
    }*/
}