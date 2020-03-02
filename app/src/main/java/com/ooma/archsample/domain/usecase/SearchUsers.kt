package com.ooma.archsample.domain.usecase

import com.ooma.archsample.data.model.UsersSearchResult
import com.ooma.archsample.data.repository.UserRepository
import io.reactivex.Single

class SearchUsers(private val repository: UserRepository) : BaseUseCase<UsersSearchResult, SearchUsers.Params>() {

    override fun run(params: Params): Single<out UsersSearchResult> =
        repository.searchUsers(params.searchText)

    data class Params(val searchText: String)
}