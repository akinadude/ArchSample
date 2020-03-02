package com.ooma.archsample.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.data.source.network.GithubApi
import com.ooma.archsample.domain.usecase.GetUserProfile
import com.ooma.archsample.extension.disposeBy

class ProfileViewModel : BaseViewModel() {

    private val githubApi = GithubApi()
    private val repository = UserRepository(githubApi)
    private val getUserProfile = GetUserProfile(repository)

    private val _profile: MutableLiveData<User> by lazy { MutableLiveData<User>() }

    //todo Done! Need common solution for params passed to a use case
    //todo Done! Get the idea of passing data to ViewModel

    //todo Done! This should be in base use case which is intended for getting data in background
    // subscribeOn(Schedulers.io())
    // observeOn(AndroidSchedulers.mainThread())

    //todo Done! Use docdoc trick disposeBy

    //todo Done! Transforming (mapping) models between layers
    // It turns out that there is a necessity to map models from data layer into format which is suitable for the presentation layer.
    // May be later we will need mapping in the domain layer.

    //todo Done! Implement search by user screen
    // The first screen contains a single button 'start'
    // The second screen is a list of searched users
    // The third screen is a single user screen
    // Implement navigation

    //todo -> Need to create base use case and several types of use cases. Read the article about that.

    //todo Result sealed class and result from the retrofit request.
    //todo What about loading state?

    //todo RxJava and MVVM. Can they coexist? Observable vs live data

    //todo Read about repository implementation. Then implement it properly.

    //todo Revisit clean arch layers' boundaries and package structure

    //todo Clear way of error handling using Rx (look at Fernando Cejas sample and docdoc app)

    //todo Implement Environment class (for storing current backend url)

    //todo Implement API related classes the same way as in docdoc app

    //todo Implement resource provider (context, ...). See docdoc app.

    //todo Introduce DI. Koin or Dagger2?

    //todo Investigate navigation

    //todo Implement login screen and reproduce the case when auth token is expired

    val profile: MutableLiveData<User>
        get() = _profile

    fun getUserProfile(username: String) {
        getUserProfile.execute(
                GetUserProfile.Params(username),
                { handleUserProfile(it) },
                {
                    Log.d("TAG", "Error getting profile ${it.message}")
                    handleFailure(it)
                }
        ).disposeBy(this)
    }

    private fun handleUserProfile(profile: User) {
        _profile.value = profile
    }
}