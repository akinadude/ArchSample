package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ooma.archsample.Navigator
import com.ooma.archsample.data.repository.UserDataGateway
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.data.source.network.GithubApi
import com.ooma.archsample.data.source.persistence.MemoryCacheApi
import com.ooma.archsample.domain.model.SearchItem
import com.ooma.archsample.domain.model.SearchUserSuggestion
import com.ooma.archsample.domain.usecase.SearchUsers
import com.ooma.archsample.extension.disposeBy
import com.ooma.archsample.presentation.mapper.SearchUsersMapper

class SearchViewModel(private val navigator: Navigator) : RequestDataViewModel() {

    private val githubApi = GithubApi()
    private val memoryCache = MemoryCacheApi()
    private val remote = UserDataGateway(githubApi)
    private val local = UserDataGateway(memoryCache)
    private val repository = UserRepository(remote, local)
    private val searchUsers = SearchUsers(repository)
    private val mapper = SearchUsersMapper()

    private val _searchSuggestions: MutableLiveData<List<SearchItem>> by lazy { MutableLiveData<List<SearchItem>>() }
    private val _initial: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    val searchSuggestions: MutableLiveData<List<SearchItem>>
        get() = _searchSuggestions

    val initial: MutableLiveData<Unit>
        get() = _initial

    fun setSearchText(s: String) {
        if (s.isNotBlank()) {
            setLoading()
            searchUsers.setSearchText(s)
        } else {
            _initial.value = Unit
            resubscribe()
        }
    }

    //todo Done! Seeing previous search result issue

    //todo Done! Deal with empty state. Show text: start to type text in EditText.
    // State issue was born here

    //todo Done! Deal with distinctUntilChanged()

    //todo Deal nothing found and error state

    /*
    todo State issue. Read about UDF using ViewModel and LiveData
    States:
    Initial                             no key for searching entered so far
    Loading                             showing progress while obtaining data
    SearchResult(List<User> nonempty)   users are found by key
    SearchResult(List<User> empty)      nothing found by key
    Error(Throwable: t)                 error occurred during getting search result
    */

    fun observeSearchView() {
        searchUsers.observeSubject(
            { _searchSuggestions.value = mapper.toSearchResult(it.items) },
            {
                //todo To set an error state there is no need to call clear
                clear()
                setFailure(it)
                resubscribe()
            }
        ).disposeBy(this)
    }

    fun onUserSuggestionClick(suggestion: SearchUserSuggestion) {
        navigator.openUserProfileScreen(suggestion)
    }

    private fun resubscribe() {
        searchUsers.dispose()
        observeSearchView()
    }

    private fun clear() {
        _searchSuggestions.value = emptyList()
    }
}