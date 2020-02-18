package com.ooma.archsample.domain

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.source.network.GithubApi
import io.reactivex.Single

class UserInteractor(private val api: GithubApi) {

    fun getUser(username: String): Single<User> = api.getUser(username)
}