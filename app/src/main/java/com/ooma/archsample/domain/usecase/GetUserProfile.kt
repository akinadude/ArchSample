package com.ooma.archsample.domain.usecase

import com.ooma.archsample.data.model.UserProfile
import com.ooma.archsample.data.repository.UserRepository
import io.reactivex.Single

class GetUserProfile(private val repository: UserRepository) :
    UseCase<UserProfile, GetUserProfile.Params>() {

    override fun run(params: Params): Single<UserProfile> =
        repository.getUserProfile(params.username)

    data class Params(val username: String)
}