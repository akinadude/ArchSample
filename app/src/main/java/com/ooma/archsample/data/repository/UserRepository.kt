package com.ooma.archsample.data.repository

import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.data.source.network.GithubApi
import io.reactivex.Single

class UserRepository(private val api: GithubApi) {

    fun getUserProfile(username: String): Single<UserProfile> = api.getUserProfile(username)
}