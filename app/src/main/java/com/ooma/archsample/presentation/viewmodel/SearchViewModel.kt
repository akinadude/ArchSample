package com.ooma.archsample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ooma.archsample.Navigator
import com.ooma.archsample.data.repository.UserDataGateway
import com.ooma.archsample.data.repository.UserRepository
import com.ooma.archsample.data.source.network.GithubApi
import com.ooma.archsample.data.source.persistence.MemoryCacheApi
import com.ooma.archsample.data.source.persistence.NothingFound
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

    private val _searchSuggestions: MutableLiveData<List<SearchUserSuggestion>> by lazy { MutableLiveData<List<SearchUserSuggestion>>() }

    val searchSuggestions: MutableLiveData<List<SearchUserSuggestion>>
        get() = _searchSuggestions

    fun setSearchText(s: String) {
        if (s.isNotBlank()) {
            setLoading()
            searchUsers.setSearchText(s)
        } else {
            clear()
            setFailure(NothingFound)
        }
    }

    fun observeSearchView() {
        searchUsers.observeSubject(
            { _searchSuggestions.value = mapper.toSuggestions(it.items) },
            {
                clear()
                setFailure(it)
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