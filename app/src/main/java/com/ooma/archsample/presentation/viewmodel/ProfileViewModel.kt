package com.ooma.archsample.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ooma.archsample.Result
import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.data.source.network.GithubApi
import com.ooma.archsample.domain.UserInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val githubApi = GithubApi()
    private val interactor = UserInteractor(githubApi)

    private val _profile: MutableLiveData<Result<UserProfile>> by lazy { MutableLiveData<Result<UserProfile>>() }

    //todo Result sealed class and result from the retrofit request
    //todo What about loading state?

    //todo RxJava and MVVM

    //todo Introduce base use case/interactor

    val profile: MutableLiveData<Result<UserProfile>>
        get() = _profile

    fun getUserProfile(username: String) {
        val disposable = interactor.getUserProfile(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _profile.value = Result.Success(it)
                }, {
                    Log.d("TAG", "Error getting profile ${it.message}")
                    _profile.value = Result.Failure(it)
                })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}