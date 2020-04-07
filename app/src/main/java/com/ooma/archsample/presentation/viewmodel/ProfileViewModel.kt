package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.repository.UserDataGateway
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.data.source.network.GithubApi
import com.ooma.archsample.data.source.persistence.MemoryCacheApi
import com.ooma.archsample.domain.usecase.GetUserProfile
import com.ooma.archsample.extension.disposeBy

class ProfileViewModel : BaseViewModel() {

    private val githubApi = GithubApi()
    private val memoryCache = MemoryCacheApi()
    private val remote = UserDataGateway(githubApi)
    private val local = UserDataGateway(memoryCache)
    private val repository = UserRepository(remote, local)
    private val getUserProfile = GetUserProfile(repository)

    private val _profile: MutableLiveData<User> by lazy { MutableLiveData<User>() }

    //todo Done! Need common solution for params passed to a use case
    //todo Done! Get the idea of passing data to ViewModel

    //todo Done! This should be in base use case which is intended for getting data in background
    // subscribeOn(Schedulers.io())
    // observeOn(AndroidSchedulers.mainThread())

    //todo Done! Implement search by user screen
    // The first screen contains a single button 'start'
    // The second screen is a list of searched users
    // The third screen is a single user screen
    // Implement simple navigation

    //todo Done! Get rid of deprecated ViewModelProviders.of

    //todo Read about repository implementation. Then implement it properly (remote, database, in-memory).
    // Done! Implement common interface for remote and in-memory data sources
    // Done! Implement simple in-memory cache
    // Read about complex solutions in Gitfox app or in articles
    // Implement database related solution (Room, SqlBright, ...)

    //todo Doing... Improve SearchViewModel: all rx related work should be done within use case.
    // Currently it is placed within the view and the view model

    //todo Next... Need to create base use case and several types of use cases. Read the article about that.

    //todo Next... Dive deeply into use case field. What are they used for
    // getting data
    // core business logic of the app
    // navigation
    // ...

    //todo Transforming (mapping) models between should be within domain layer

    //todo Result sealed class and result from the retrofit request.
    //todo What about loading state?

    //todo RxJava and MVVM. Can they coexist? Observable vs LiveData types

    //todo Revisit clean arch layers' boundaries and package structure

    //todo Clear way of error handling using Rx (look at Fernando Cejas sample and docdoc app)

    //todo Implement Environment class (for storing current backend url)

    //todo Implement API related classes the same way as in docdoc app

    //todo Implement resource provider (context, ...). See docdoc app.

    //todo Introduce DI. Koin or Dagger2?

    //todo Investigate navigation

    //todo Implement login screen and reproduce the case when auth token is expired

    //todo Think about modularisation. Read about feature-module approach.

    val profile: MutableLiveData<User>
        get() = _profile

    fun getUserProfile(username: String) {
        setLoading()
        getUserProfile.execute(
            GetUserProfile.Params(username),
            { setUserProfile(it) },
            { setFailure(it) }
        ).disposeBy(this)
    }

    private fun setUserProfile(profile: User) {
        _profile.value = profile
    }
}