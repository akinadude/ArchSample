package com.ooma.archsample.data.source.persistence

import android.util.Log
import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

//framework layer
class FakeDatabase {

    private var usersCache: MutableMap<String, User> = mutableMapOf()
    private var searchedUsersCache: MutableMap<String, List<User>> = mutableMapOf()

    //todo Here must be an instance of the database to invoke on it methods which return data
    // Similar to GithubApi

    fun saveUserProfile(username: String, user: User) {
        usersCache[username] = user
    }

    fun getUserProfile(username: String): Single<User> = Single.create { emitter ->
        val user = usersCache[username]
        user?.let {
            emitter.onSuccess(user)
        } ?: emitter.onError(Throwable("NO_CACHED_DATA"))
    }

    fun saveSearchedUsers(searchText: String, list: List<User>) {
        searchedUsersCache[searchText] = list
    }

    fun getSearchedUsers(searchText: String): Single<UsersSearchResult> = Single.create { emitter ->
        val list = searchedUsersCache[searchText]
        list?.let {
            if (it.isEmpty()) {
                emitter.onError(Throwable("EMPTY_DATA"))
            } else {
                Log.d("REPO", "Fetching from fake database")
                val searchResult = UsersSearchResult(it.size.toLong(), false, it)
                emitter.onSuccess(searchResult)
            }
        } ?: emitter.onError(Throwable("NO_CACHED_DATA"))
    }
}