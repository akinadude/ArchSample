package com.ooma.archsample.data.source.persistence

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserDataDispatcher
import io.reactivex.Single

//Framework layer
class DatabaseApi : UserDataDispatcher {

    private val database = FakeDatabase()

    override fun getUserProfile(username: String): Single<User> = database.getUserProfile(username)

    override fun searchUsers(searchText: String): Single<UsersSearchResult> =
        database.getSearchedUsers(searchText)

    override fun saveUserProfile(username: String, user: User) {
        database.saveUserProfile(username, user)
    }

    override fun saveSearchedUsers(searchText: String, users: List<User>) {
        database.saveSearchedUsers(searchText, users)
    }
}