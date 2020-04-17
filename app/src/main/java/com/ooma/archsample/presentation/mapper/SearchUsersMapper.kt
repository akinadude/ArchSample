package com.ooma.archsample.presentation.mapper

import com.ooma.archsample.data.model.User
import com.ooma.archsample.domain.model.SearchResult
import com.ooma.archsample.domain.model.SearchUserSuggestion

class SearchUsersMapper {

    fun toSearchResult(users: List<User>): List<SearchResult> =
        users.map { SearchResult(SearchUserSuggestion(it.id, it.login, it.avatar_url)) }
}