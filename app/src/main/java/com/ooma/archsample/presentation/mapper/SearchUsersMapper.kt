package com.ooma.archsample.presentation.mapper

import com.ooma.archsample.data.model.User
import com.ooma.archsample.domain.model.SearchUserSuggestion

class SearchUsersMapper {

    fun toSuggestions(users: List<User>): List<SearchUserSuggestion> =
        users.map { SearchUserSuggestion(it.id, it.login, it.avatar_url) }
}