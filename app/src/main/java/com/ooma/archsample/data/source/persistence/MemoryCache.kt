package com.ooma.archsample.data.source.persistence

import androidx.collection.LruCache
import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

//framework layer
class MemoryCache {

    private val defaultNumber = 10

    private val usersLruCache: LruCache<String, User> = LruCache(defaultNumber)
    private var searchedUsersLruCache: LruCache<String, List<User>> = LruCache(defaultNumber)

    fun saveUserProfile(username: String, user: User) {
        usersLruCache.put(username, user)
    }

    fun getUserProfile(username: String): Single<User> = Single.create { emitter ->
        val user = usersLruCache.get(username)
        user?.let {
            emitter.onSuccess(it)
        } ?: emitter.onError(NoDataError)
    }

    fun saveSearchedUsers(searchText: String, list: List<User>) {
        searchedUsersLruCache.put(searchText, list)
    }

    fun getSearchedUsers(searchText: String): Single<UsersSearchResult> = Single.create { emitter ->
        val list = searchedUsersLruCache.get(searchText)
        list?.let {
            val searchResult = UsersSearchResult(it.size.toLong(), false, it)
            emitter.onSuccess(searchResult)
        } ?: emitter.onError(NoDataError)
    }
}