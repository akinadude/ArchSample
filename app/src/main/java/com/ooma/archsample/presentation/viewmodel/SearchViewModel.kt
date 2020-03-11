package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ooma.archsample.Navigator
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.data.source.network.GithubApi
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.domain.usecase.SearchUsers
import com.ooma.archsample.extension.disposeBy
import com.ooma.archsample.presentation.mapper.SearchUsersMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(private val navigator: Navigator) : BaseViewModel() {

    private val githubApi = GithubApi()
    private val repository = UserRepository(githubApi)
    private val searchUsers = SearchUsers(repository)
    private val mapper = SearchUsersMapper()

    private val _searchSuggestions: MutableLiveData<List<SearchUserSuggestion>> by lazy { MutableLiveData<List<SearchUserSuggestion>>() }

    val searchSuggestions: MutableLiveData<List<SearchUserSuggestion>>
        get() = _searchSuggestions

    fun performSearch(text: String, subject: PublishSubject<String>) {
        if (text.isNotEmpty()) {
            setLoading()
            subject.onNext(text)
        } else {
            clear()
        }
    }

    fun subscribeToSubject(subject: PublishSubject<String>) {
        //todo Try to move it to the use case class
        subject.debounce(300, TimeUnit.MILLISECONDS)
                .switchMap { searchText ->
                    searchUsers.run(SearchUsers.Params(searchText))
                            .toObservable()
                            .subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { _searchSuggestions.value = mapper.toSuggestions(it.items) },
                        {
                            clear()
                            _failure.value = it
                            subscribeToSubject(subject)
                        }
                ).disposeBy(this)
    }

    fun onUserSuggestionClick(suggestion: SearchUserSuggestion) {
        navigator.openUserProfileScreen(suggestion)
    }

    private fun clear() {
        _searchSuggestions.value = emptyList()
    }
}