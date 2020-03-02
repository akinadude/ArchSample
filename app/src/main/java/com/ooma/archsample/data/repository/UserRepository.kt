package com.ooma.archsample.data.repository

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.source.network.GithubApi
import io.reactivex.Single

class UserRepository(private val api: GithubApi) {

    fun getUserProfile(username: String): Single<User> = api.getUserProfile(username)

    fun searchUsers(searchText: String): Single<UsersSearchResult> = api.searchUsers(searchText)
}