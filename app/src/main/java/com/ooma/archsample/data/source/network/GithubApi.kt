package com.ooma.archsample.data.source.network

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserDataAccessor
import io.reactivex.Single

//Framework layer
class GithubApi : UserDataAccessor {

    private val service = GithubApiFactory.createRetrofitService()

    override fun getUserProfile(username: String): Single<User> = service.getUserProfile(username)

    override fun searchUsers(searchText: String): Single<UsersSearchResult> = service.searchUsers(searchText)

    override fun saveUserProfile(username: String, user: User) {
        TODO("Not yet implemented")
    }

    override fun saveSearchedUsers(searchText: String, users: List<User>) {
        TODO("Not yet implemented")
    }
}
//introduce RxJava
//read an article/see a repo/watch a video about RxJava and MVVM
//make it work with MVVM

//review retrofit related architecture (api, factory, service)
//review package structure (Where should User and UserContainer be placed?)