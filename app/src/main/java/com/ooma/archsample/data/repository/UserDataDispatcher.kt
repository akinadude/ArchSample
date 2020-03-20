package com.ooma.archsample.data.repository

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

//Data layer
interface UserDataDispatcher {
    fun getUserProfile(username: String): Single<User>
    fun searchUsers(searchText: String): Single<UsersSearchResult>
    fun saveUserProfile(username: String, user: User)
    fun saveSearchedUsers(searchText: String, users: List<User>)
}