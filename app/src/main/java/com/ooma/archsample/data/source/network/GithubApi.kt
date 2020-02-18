package com.ooma.archsample.data.source.network

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.model.UsersSearchResult
import io.reactivex.Single

class GithubApi {

    private val service = GithubFactory.createRetrofitService()

    fun getUser(username: String): Single<User> = service.getUser(username)

    fun searchUsers(searchText: String): Single<UsersSearchResult> = service.searchUsers(searchText)
}
//introduce RxJava
//read an article/see a repo/watch a video about RxJava and MVVM
//make it work with MVVM

//review retrofit related architecture (api, factory, service)
//review package structure (Where should User and UserContainer be placed?)