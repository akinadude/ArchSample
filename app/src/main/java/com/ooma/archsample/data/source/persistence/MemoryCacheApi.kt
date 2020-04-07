package com.ooma.archsample.data.source.persistence

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserDataAccessor
import io.reactivex.Single

//Framework layer
class MemoryCacheApi : UserDataAccessor {

    private val memoryCache = MemoryCache()

    override fun getUserProfile(username: String): Single<User> = memoryCache.getUserProfile(username)

    override fun searchUsers(searchText: String): Single<UsersSearchResult> =
        memoryCache.getSearchedUsers(searchText)

    override fun saveUserProfile(username: String, user: User) {
        memoryCache.saveUserProfile(username, user)
    }

    override fun saveSearchedUsers(searchText: String, users: List<User>) {
        memoryCache.saveSearchedUsers(searchText, users)
    }
}