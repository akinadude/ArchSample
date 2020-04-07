package com.ooma.archsample.data.repository

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

//Data layer
class UserDataGateway(private val accessor: UserDataAccessor) {

    fun getUserProfile(username: String): Single<User> = accessor.getUserProfile(username)

    fun searchUsers(searchText: String): Single<UsersSearchResult> =
        accessor.searchUsers(searchText)

    fun saveUserProfile(username: String, user: User) = accessor.saveUserProfile(username, user)

    fun saveSearchedUsers(searchText: String, users: List<User>) =
        accessor.saveSearchedUsers(searchText, users)
}

//todo Done! Try to use LruCache structure for memory cache or another in memory cache solution
//todo Doing... Action item about base view model from the minutes (email).

//todo Need to investigate the ideas from articles about independence of framework/libs.
// The article by Antonio Lieva
// Gitfox app

//todo Use Room as a database solution for disk caching