package com.ooma.archsample.domain

import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.data.source.network.GithubApi
import io.reactivex.Single

class UserInteractor(private val api: GithubApi) {

    fun getUserProfile(username: String): Single<UserProfile> = api.getUserProfile(username)
}