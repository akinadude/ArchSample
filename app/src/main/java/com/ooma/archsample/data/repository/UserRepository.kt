package com.ooma.archsample.data.repository

import android.util.Log
import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.source.persistence.NoData
import io.reactivex.Single

//todo There can be different implementation of the strategy for getting/caching data
// + cached data can have time-to-live property
class UserRepository(
    private val remote: UserDataGateway,
    private val memory: UserDataGateway
) {
    //todo there should be root class for all models (consider data and domain levels)

    //todo Try Maybe idea
    fun getUserProfile(username: String): Single<User> =
        memory.getUserProfile(username)
            .doOnSuccess {
                Log.i("REPO", "Fetching from memory by key $username.")
            }.onErrorResumeNext { error ->
                when (error) {
                    is NoData -> {
                        Log.i("REPO", "Fetching from remote by key $username.")
                        remote.getUserProfile(username)
                            .doOnSuccess {
                                Log.i("REPO", "Caching data by key $username.")
                                memory.saveUserProfile(username, it)
                            }
                    }
                    else -> Single.error(error)
                }
            }

    fun searchUsers(searchText: String): Single<UsersSearchResult> =
        memory.searchUsers(searchText)
            .doOnSuccess {
                Log.i("REPO", "Fetching from memory by key $searchText.")
            }.onErrorResumeNext { error ->
                when (error) {
                    is NoData -> {
                        Log.i("REPO", "Fetching from remote by key $searchText.")
                        remote.searchUsers(searchText)
                            .doOnSuccess {
                                Log.i("REPO", "Caching data by key $searchText.")
                                memory.saveSearchedUsers(searchText, it.items)
                            }
                    }
                    else -> Single.error(error)
                }
            }
}