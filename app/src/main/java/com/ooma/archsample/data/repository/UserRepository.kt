package com.ooma.archsample.data.repository

import android.util.Log
import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

//todo There can be different implementation of the strategy for getting/caching data
// + cached data can have time-to-live property
class UserRepository(
    private val remote: UserDataGateway,
    private val local: UserDataGateway
) {
    //todo there should be root class for all models (consider data and domain levels)

    fun getUserProfile(username: String): Single<User> =
        local.getUserProfile(username)
            .onErrorResumeNext { error ->
                if (error.message == "NO_CACHED_DATA" || error.message == "EMPTY_DATA") {
                    Log.d("REPO", "Fetching from remote")
                    remote.getUserProfile(username)
                        .doOnSuccess { local.saveUserProfile(username, it) }
                } else {
                    Single.error(error)
                }
            }

    fun searchUsers(searchText: String): Single<UsersSearchResult> =
        local.searchUsers(searchText)
            .onErrorResumeNext { error ->
                if (error.message == "NO_CACHED_DATA" || error.message == "EMPTY_DATA") {
                    Log.d("REPO", "Fetching from remote")
                    remote.searchUsers(searchText)
                        .doOnSuccess { local.saveSearchedUsers(searchText, it.items) }
                } else {
                    Single.error(error)
                }
            }
}