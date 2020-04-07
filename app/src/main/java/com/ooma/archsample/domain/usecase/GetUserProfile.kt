package com.ooma.archsample.domain.usecase

import com.ooma.archsample.data.model.User
import com.ooma.archsample.data.repository.UserRepository
import io.reactivex.Single

class GetUserProfile(private val repository: UserRepository) :
        SingleUseCase<User, GetUserProfile.Params>() {

    override fun buildUseCaseSingle(params: Params): Single<User> = repository.getUserProfile(params.username)

    data class Params(val username: String)
}