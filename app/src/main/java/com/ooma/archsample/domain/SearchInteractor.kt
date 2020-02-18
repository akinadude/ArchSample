package com.ooma.archsample.domain

import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.source.network.GithubApi
import io.reactivex.Single

class SearchInteractor(private val api: GithubApi) {

    fun searchUsers(searchText: String): Single<UsersSearchResult> = api.searchUsers(searchText)
}