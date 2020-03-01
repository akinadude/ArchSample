package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ooma.archsample.data.model.UserProfile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel : BaseViewModel() {

    private val _searchUsers: MutableLiveData<List<UserProfile>> by lazy { MutableLiveData<List<UserProfile>>() }

    val searchUsers: MutableLiveData<List<UserProfile>>
        get() = _searchUsers

    fun performSearch(text: String, subject: PublishSubject<String>) {
        if (text.isNotEmpty()) {
            subject.onNext(text)
        } else {
            clear()
        }
    }

    fun subscribeToSubject(subject: PublishSubject<String>) {
        subject.debounce(300, TimeUnit.MILLISECONDS)
            .switchMap { searchText ->
                manager.searchUsers(searchText)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //getView()?.showSearchUsers(it.items)
                    _searchUsers.value = it.items
                },
                {
                    clear()
                    //getView()?.showError(it)
                    failure.value = it
                    subscribeToSubject(subject)
                }
            ).disposeBy(this)
    }

    private fun clear() {
        _searchUsers.value = emptyList()
    }
}